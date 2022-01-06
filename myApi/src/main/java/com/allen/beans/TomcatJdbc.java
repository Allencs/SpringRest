package com.allen.beans;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class TomcatJdbc {

    @Bean
    public DataSource dataSource(){
        PoolProperties p = new PoolProperties();
        p.setUrl("jdbc:mysql://localhost:3306/master_vehicle?serverTimezone=GMT%2B8&characterEncoding=utf8");
        p.setDriverClassName("com.mysql.cj.jdbc.Driver");
        p.setUsername("root");
        p.setPassword("123456");
        p.setTestWhileIdle(true);
        p.setTestOnBorrow(false);
        p.setValidationQuery("SELECT 1");
        p.setTestOnReturn(false);
        p.setValidationInterval(30000);
        p.setTimeBetweenEvictionRunsMillis(30000);
        p.setInitialSize(10);
        p.setMaxActive(30);
        p.setMinIdle(10);
        p.setMaxIdle(10);
        p.setMaxWait(3000);
        p.setRemoveAbandonedTimeout(60);
        p.setLogAbandoned(true);
        p.setRemoveAbandoned(true);
        DataSource datasource = new DataSource();
        datasource.setPoolProperties(p);
        return datasource;
    }
}
