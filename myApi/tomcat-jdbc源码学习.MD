## Tomcat JDBC连接池

### 配置参数详解
> https://wiki.jikexueyuan.com/project/tomcat/tomcat-jdbc-pool.html

### Connection代理对象
`dataSource.getConnection()`获取到的`Connection`对象是个代理对象；
``` package org.apache.tomcat.jdbc.pool.ConnectionPool;
/**
     * Borrows a connection from the pool. If a connection is available (in the idle queue) or the pool has not reached
     * {@link PoolProperties#maxActive maxActive} connections a connection is returned immediately.
     * If no connection is available, the pool will attempt to fetch a connection for {@link PoolProperties#maxWait maxWait} milliseconds.
     * @return Connection - a java.sql.Connection/javax.sql.PooledConnection reflection proxy, wrapping the underlying object.
     * @throws SQLException - if the wait times out or a failure occurs creating a connection
     */
    public Connection getConnection() throws SQLException {
        //check out a connection
        PooledConnection con = borrowConnection(-1,null,null);
        // 获取连接对象的时候，返回的是一个重新封装过的Connection，即代理对象
        return setupConnection(con);
    }
```
构造代理对象的方法如下：
```package org.apache.tomcat.jdbc.pool.ConnectionPool
/**
     * configures a pooled connection as a proxy.
     * This Proxy implements {@link java.sql.Connection} and {@link javax.sql.PooledConnection} interfaces.
     * All calls on {@link java.sql.Connection} methods will be propagated down to the actual JDBC connection except for the
     * {@link java.sql.Connection#close()} method.
     * @param con a {@link PooledConnection} to wrap in a Proxy
     * @return a {@link java.sql.Connection} object wrapping a pooled connection.
     * @throws SQLException if an interceptor can't be configured, if the proxy can't be instantiated
     */
    protected Connection setupConnection(PooledConnection con) throws SQLException {
        //fetch previously cached interceptor proxy - one per connection
        JdbcInterceptor handler = con.getHandler();
        if (handler==null) {
            if (jmxPool != null) {
                con.createMBean();
            }
            //build the proxy handler
            handler = new ProxyConnection(this,con,getPoolProperties().isUseEquals());
            //set up the interceptor chain
            PoolProperties.InterceptorDefinition[] proxies = getPoolProperties().getJdbcInterceptorsAsArray();
            for (int i=proxies.length-1; i>=0; i--) {
                try {
                    //create a new instance
                    JdbcInterceptor interceptor = proxies[i].getInterceptorClass().getConstructor().newInstance();
                    //configure properties
                    interceptor.setProperties(proxies[i].getProperties());
                    //setup the chain
                    interceptor.setNext(handler);
                    //call reset
                    interceptor.reset(this, con);
                    //configure the last one to be held by the connection
                    handler = interceptor;
                }catch(Exception x) {
                    SQLException sx = new SQLException("Unable to instantiate interceptor chain.");
                    sx.initCause(x);
                    throw sx;
                }
            }
            //cache handler for the next iteration
            con.setHandler(handler);
        } else {
            JdbcInterceptor next = handler;
            //we have a cached handler, reset it
            while (next!=null) {
                next.reset(this, con);
                next = next.getNext();
            }
        }
        // setup statement proxy
        if (getPoolProperties().getUseStatementFacade()) {
            handler = new StatementFacade(handler);
        }
        // 从这块代码可以知道，每次调用getConnection方法都会创建一个新的Proxy对象
        try {
            getProxyConstructor(con.getXAConnection() != null);
            //create the proxy
            //TODO possible optimization, keep track if this connection was returned properly, and don't generate a new facade
            Connection connection = null;
            if (getPoolProperties().getUseDisposableConnectionFacade() ) {
                connection = (Connection)proxyClassConstructor.newInstance(new Object[] { new DisposableConnectionFacade(handler) });
            } else {
                connection = (Connection)proxyClassConstructor.newInstance(new Object[] {handler});
            }
            //return the connection
            return connection;
        }catch (Exception x) {
            SQLException s = new SQLException();
            s.initCause(x);
            throw s;
        }

    }
```
代理对象的核心是，可以更改`Connection`对象的操作逻辑，比如对于`close`方法，代理对象执行`close`方法时做了拦截，避免连接的关闭而是归还到连接池中。
核心代码如下：
```org.apache.tomcat.jdbc.pool.ProxyConnection
public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 判断调用了Connection的那些方法，并自定义了新的调用逻辑
        if (compare(ISCLOSED_VAL,method)) {
            return Boolean.valueOf(isClosed());
        }
        if (compare(CLOSE_VAL,method)) {
            if (connection==null)
             {
              return null; //noop for already closed.
            }
            PooledConnection poolc = this.connection;
            this.connection = null;
            pool.returnConnection(poolc);
            return null;
        } else if (compare(TOSTRING_VAL,method)) {
            return this.toString();
        } else if (compare(GETCONNECTION_VAL,method) && connection!=null) {
            return connection.getConnection();
        } else if (method.getDeclaringClass().isAssignableFrom(XAConnection.class)) {
            try {
                return method.invoke(connection.getXAConnection(),args);
            }catch (Throwable t) {
                if (t instanceof InvocationTargetException) {
                    throw t.getCause() != null ? t.getCause() : t;
                } else {
                    throw t;
                }
            }
        }
        if (isClosed()) {
          throw new SQLException("Connection has already been closed.");
        }
        if (compare(UNWRAP_VAL,method)) {
            return unwrap((Class<?>)args[0]);
        } else if (compare(ISWRAPPERFOR_VAL,method)) {
            return Boolean.valueOf(this.isWrapperFor((Class<?>)args[0]));
        }
        try {
            PooledConnection poolc = connection;
            if (poolc!=null) {
                return method.invoke(poolc.getConnection(),args);
            } else {
                throw new SQLException("Connection has already been closed.");
            }
        }catch (Throwable t) {
            if (t instanceof InvocationTargetException) {
                throw t.getCause() != null ? t.getCause() : t;
            } else {
                throw t;
            }
        }
    }
```
`ProxyConnection`类继承了`JdbcInterceptor`并实现了`InvocationHandler`接口。

package org.apache.tomcat.jdbc.pool.ConnectionPool的returnConnection方法实现的是归还连接池的逻辑，根据代码可知，可以通过判断busy
队列的大小来知道连接池是否已经使用完。如果busy的size等于MaxActive，则JDBC连接池已耗尽。
```package org.apache.tomcat.jdbc.pool.ConnectionPool
/**
     * Returns a connection to the pool
     * If the pool is closed, the connection will be released
     * If the connection is not part of the busy queue, it will be released.
     * If {@link PoolProperties#testOnReturn} is set to true it will be validated
     * @param con PooledConnection to be returned to the pool
     */
    protected void returnConnection(PooledConnection con) {
        if (isClosed()) {
            //if the connection pool is closed
            //close the connection instead of returning it
            release(con);
            return;
        } //end if

        if (con != null) {
            try {
                returnedCount.incrementAndGet();
                con.lock();
                if (con.isSuspect()) {
                    if (poolProperties.isLogAbandoned() && log.isInfoEnabled()) {
                        log.info("Connection(" + con + ") that has been marked suspect was returned."
                                + " The processing time is " + (System.currentTimeMillis()-con.getTimestamp()) + " ms.");
                    }
                    if (jmxPool!=null) {
                        jmxPool.notify(org.apache.tomcat.jdbc.pool.jmx.ConnectionPool.SUSPECT_RETURNED_NOTIFICATION,
                                "Connection(" + con + ") that has been marked suspect was returned.");
                    }
                }
                // 归还连接的时候会将连接对象从busy中移除，busy是个BlockingQueue
                if (busy.remove(con)) {

                    if (!shouldClose(con,PooledConnection.VALIDATE_RETURN)) {
                        con.clearWarnings();
                        con.setStackTrace(null);
                        con.setTimestamp(System.currentTimeMillis());
                        if (((idle.size()>=poolProperties.getMaxIdle()) && !poolProperties.isPoolSweeperEnabled()) || (!idle.offer(con))) {
                            if (log.isDebugEnabled()) {
                                log.debug("Connection ["+con+"] will be closed and not returned to the pool, idle["+idle.size()+"]>=maxIdle["+poolProperties.getMaxIdle()+"] idle.offer failed.");
                            }
                            release(con);
                        }
                    } else {
                        if (log.isDebugEnabled()) {
                            log.debug("Connection ["+con+"] will be closed and not returned to the pool.");
                        }
                        release(con);
                    } //end if
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug("Connection ["+con+"] will be closed and not returned to the pool, busy.remove failed.");
                    }
                    release(con);
                }
            } finally {
                con.unlock();
            }
        } //end if
    } //checkIn
```