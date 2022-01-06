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

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan("com.allen")
public class DataSourceTests {
    @Autowired
    private DataSource dataSource;

    @Test
    public void testConnection() throws Exception {
        System.out.println(dataSource);
        Connection conn = dataSource.getConnection();
        String sql = "select * from master_vehicle.vehicle limit 5";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ResultSet res = preparedStatement.executeQuery();
        // 遍历查询结果集
        while(res.next()){
            System.out.println(res.getString("vin"));
        }
        res.close();
        preparedStatement.close();
        conn.close();
    }
}
