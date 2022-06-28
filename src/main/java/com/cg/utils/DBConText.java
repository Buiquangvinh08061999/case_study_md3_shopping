package com.cg.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConText {
    public static Connection getConnection(){
        String hostName = "localhost";
        String dtbName = "c0222k1_management?useUnicode=true&characterEncoding=UTF-8";
        String userName = "root";
        String password = "153191Vn";
        return getSqlConnection(hostName,dtbName,userName,password);
    }


    public static Connection getSqlConnection(String hostName,String dtbName,String userName, String password){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String URL = "jdbc:mysql://" + hostName + ":3306/"+ dtbName;
            return DriverManager.getConnection(URL, userName,password);

        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }



    public static void main(String[] args) {
        if(getConnection() != null){
            System.out.println("Ket noi thanh cong");
        }else {
            System.out.println("Ket noi that bai");
        }
        System.out.println("hihi");
    }









    public static void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
