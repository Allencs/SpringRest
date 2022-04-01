package com.allen;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//@SpringBootTest
//@ComponentScan("com.allen")
public class DataSourceTests {
    @Autowired
    private DataSource dataSource;

    @Test
    public void testConnection() throws Exception {
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Thread thread = new Thread(() -> {
                Connection connection = null;
                try {
                    connection = dataSource.getConnection();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                PreparedStatement preparedStatement = null;
                try {
                    preparedStatement = connection.prepareStatement("select * from master_vehicle.vehicle limit 5");
                    ResultSet resultSet = preparedStatement.executeQuery();
                    resultSet.next();
                    System.out.println("测试查询结果 - " + Thread.currentThread().getName() + "：" + resultSet.getString("vin"));
//                    Thread.sleep(5000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            threads.add(thread);
        }

        for (Thread t : threads) {
            t.start();
//            t.join();
        }
        for (Thread t : threads) {
//            t.start();
            t.join();
        }
        System.out.println("子线程执行结束");
        Thread.sleep(5000);
    }
}
