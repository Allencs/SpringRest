package com.allen;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan("com.allen")
public class DataSourceTests {
    @Autowired
    private DataSource dataSource;

    @Test
    public void testConnection() throws Exception {
        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
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
                    System.out.println(resultSet.getString("vin"));
                    Thread.sleep(120000);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
