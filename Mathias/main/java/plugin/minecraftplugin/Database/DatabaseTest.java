package plugin.minecraftplugin.Database;

import java.sql.*;
import java.sql.Connection;

/*
!------------------------------------------------!//
            Java File for SQL tests
!------------------------------------------------!//
*/

public class DatabaseTest {
    public static void main(String[] args) {
        DatabaseTest getquery = new DatabaseTest();
        getquery.GetInt();
    }

    public static String url = "jdbc:mysql://178.63.127.184:3306/s4973_Mazzobase";
    public static String user = "u4973_9jAVGd7olq";
    public static String password = "2Tit3diOVm!^^nHRpYB0N=W3";
    public static int amount = 100;

    public void GetInt() {
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement statement = conn.createStatement();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

