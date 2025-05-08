package com.example.notesspeech;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class mysql {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "duck", "br1nG_Duck5!");
        Statement st = con.createStatement();
        System.out.println("Connection success");
    }

}
