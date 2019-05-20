package com.sbt;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        try (Connection connection =
                     DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "")) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from organization");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("name"));
                System.out.println(resultSet.getLong("inn"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
