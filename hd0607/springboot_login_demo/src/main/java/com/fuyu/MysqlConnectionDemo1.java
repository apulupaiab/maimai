//package com.fuyu;
//
//import java.sql.*;
//
//public class MysqlConnectionDemo1 {
//    private static final String JDBC_Driver = "com.mysql.cj.jdbc.Driver";//jdbc驱动名称
//    private static final String DB_Url = "jdbc:mysql://localhost:3306/test_db?useSSL=false&amp;allowPublicKeyRetrieval=true&amp;serverTimezone=UTC";//数据库url
//    private static final String DB_User = "root";//数据库的用户名
//    private static final String DB_Password = "123456";//数据库的密码
//
//    private static void MysqlConnection(){
//        Connection connection = null;
//        Statement statement = null;
//        try{
//            //1- 注册JDBC驱动
//            Class.forName(JDBC_Driver);
//
//            //2- 连接数据库
//            System.out.println("开始连接数据库...");
//            connection = DriverManager.getConnection(DB_Url,DB_User,DB_Password);
//
//            //3- 执行查询
//            System.out.println("开始实例化Stetement对象...");
//            statement = connection.createStatement();
//            String sql = "SELECT * FROM USER";//查询语句
//            ResultSet rs = statement.executeQuery(sql);//执行查询语句
//
//            //4- 展开结果集数据库
//            while (rs.next()){
//                int id = rs.getInt("id");
//                String name = rs.getString("name");
//                int age = rs.getInt("age");
//
//                System.out.println("id===" + id);
//                System.out.println("name===" + name);
//                System.out.println("age===" + age);
//            }
//
//            //5- 关闭连接
//            rs.close();
//            statement.close();
//            connection.close();
//
//
//        }catch(SQLException se){
//            se.printStackTrace();
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            try{
//                if(statement != null) statement.close();
//            }catch (SQLException se2){
//
//            }
//            try{
//                if(connection != null) connection.close();
//            }catch (SQLException se3){
//                se3.printStackTrace();
//            }
//        }
//    }
//    public static void main(String[] args){
//        MysqlConnection();
//    }
//}
