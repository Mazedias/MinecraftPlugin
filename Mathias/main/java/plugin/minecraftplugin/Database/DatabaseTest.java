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

    public static String url = "*******";
    public static String user = "********";
    public static String password = "*********";
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

