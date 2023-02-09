package com.su.jsekill_project;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @Classname MyTest
 * @author: 我心
 * @Description:
 * @Date 2023/1/13 23:19
 * @Created by Lenovo
 */
public class MyTest {
    public static void main(String[] args) throws SQLException {
        //com.mysql.cj.jdbc.Driver()
        Driver driver=new com.mysql.jdbc.Driver();
        String url="jdbc:mysql://192.168.234.165:3306/jsekill?characterEncoding=utf-8&serverTimezone=UTC&useSSL=false&allowMultiQueries=true&rewriteBatchedStatements=true";

        //将用户名和密码封装到 Properties中
        Properties properties=new Properties();
        properties.put("user","root");//用户名
        properties.put("password","root");//密码
//        通过驱动获取连接
        Connection connect = driver.connect(url, properties);
        System.out.println(connect);
    }
}
