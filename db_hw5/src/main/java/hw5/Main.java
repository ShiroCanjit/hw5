package hw5;

import org.jetbrains.annotations.NotNull;
import initialize.JDBCCredentials;
import initialize.FlywayInit;


import java.sql.*;
import java.util.Scanner;

public final class Main {
    private static final @NotNull JDBCCredentials CREDS = JDBCCredentials.DEFAULT;
    private static final @NotNull String FIRST_SQL = "SELECT id, name FROM organization " +
            "JOIN waybill ON waybill.sender_id = organization.id " +
            "JOIN waybill_items ON waybill_items.waybill_id = waybill.id " +
            "GROUP BY organization.id " +
            "ORDER BY SUM(waybill_items.number) LIMIT 10";
    private static final @NotNull String SECOND_SQL = "SELECT name, SUM(waybill_items.number) FROM organization " +
            "JOIN waybill ON waybill.sender_id = organization.id " +
            "JOIN waybill_items ON waybill_items.waybill_id = waybill.id" +
            "JOIN product ON product.id = waybill_items.product_id" +
            "WHERE SUM(waybill_items.number)>? AND product.id IN (?) " +
            "GROUP BY organization.id " +
            "ORDER BY SUM(waybill_items.number)";
    private static final @NotNull String THIRD_SQL = "SELECT product.id, SUM(waybill_items.number), " +
            "SUM(waybill_items.price) FROM waybill_items " +
            "JOIN product ON product.id = waybill_items.product_id" +
            "JOIN waybill ON waybill_items.waybill_id = waybill.id " +
            "JOIN organization ON organization.id = waybill.sender_id" +
            "WHERE waybill.date BETWEEN ? AND ? AND product.id IN (?) " +
            "GROUP BY product.id";
    private static final @NotNull String FOURTH_SQL = "SELECT product.id, AVG(waybill_items.price) " +
            "FROM waybill_items " +
            "JOIN product ON product.id = waybill_items.product_id" +
            "JOIN waybill ON waybill_items.waybill_id = waybill.id " +
            "JOIN organization ON organization.id = waybill.sender_id" +
            "WHERE waybill.date BETWEEN ? AND ? AND product.id IN (?) " +
            "GROUP BY product.id";
    private static final @NotNull String FIFTH_SQL = "SELECT organization.name, product.name FROM organization " +
            "LEFT OUTER JOIN waybill ON waybill.sender_id = organization.id " +
            "LEFT OUTER JOIN waybill_items ON waybill_items.waybill_id = waybill.id" +
            "LEFT OUTER JOIN product ON product.id = waybill_items.product_id" +
            "WHERE waybill.date BETWEEN ? AND ? AND product.id IN (?) " +
            "GROUP BY organization.id";

    public static void main(@NotNull String[] args) {
        FlywayInit.initDb();
        var product = "";
        int number ;
        var between_begin = "";
        var between_end = "";
        Scanner sc = new Scanner(System.in);
        System.out.println("enter minimum number of products");
        number = sc.nextInt();
        System.out.println("enter products' id");
        while (true) {
            if (sc.hasNextInt()){
                product += sc.nextLine()+", ";
            }
               else
            break;
        }
        StringBuffer st = new StringBuffer(product);
        st.delete(st.length() - 2,st.length());
        product = String.valueOf(st);
        System.out.println("enter beginning date");
        //between_begin = sc.nextLine();
        if (sc.hasNextLine()) {
                between_begin = sc.nextLine();
            System.out.println("enter ending date");
            between_end = sc.nextLine();
            }
            /*if (!Objects.equals(between_begin, "") && sc.hasNextLine()) {
                System.out.println("enter ending date");
                between_end = sc.nextLine();;
            }*/
       // between_end = sc.nextLine();

        System.out.println("First SqL:");
        try (Connection connection = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password())) {
            try (PreparedStatement statement = connection.prepareStatement(FIRST_SQL)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        System.out.println("Id: " + resultSet.getInt("organization.id")
                                + ", name: " + resultSet.getString("organization.name"));
                    }
                }
            }
            System.out.println("Second SqL:");
            try (PreparedStatement statement = connection.prepareStatement(SECOND_SQL)) {
                statement.setInt(1, number);
                statement.setString(2,product);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        System.out.println("Id: " + resultSet.getInt("product.id")
                                + ", name: " + resultSet.getString("organization.name"));
                    }
                }
            }
            System.out.println("Third SqL:");
            try (PreparedStatement statement = connection.prepareStatement(THIRD_SQL)) {
                statement.setString(1, between_begin);
                statement.setString(2, between_end);
                statement.setString(3, product);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        System.out.println("Id: " + resultSet.getInt("product.id")
                                + ", sum (number): " + resultSet.getString("SUM(waybill_items.number)")
                                + ", sum (price): " + resultSet.getString("SUM(waybill_items.price)"));
                    }
                }
            }
            System.out.println("Fourth SqL:");
            try (PreparedStatement statement = connection.prepareStatement(FOURTH_SQL)) {
                statement.setString(1, between_begin);
                statement.setString(2, between_end);
                statement.setString(3, product);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        System.out.println("Id: " + resultSet.getInt("product.id")
                                + ", avg (price): " + resultSet.getString("AVG(waybill_items.price)"));
                    }
                }
            }
            System.out.println("Fifth SqL:");
            try (PreparedStatement statement = connection.prepareStatement(FIFTH_SQL)) {
                statement.setString(1, between_begin);
                statement.setString(2, between_end);
                statement.setString(3, product);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        System.out.println("Id: " + resultSet.getInt("product.id")
                                + ", avg (price): " + resultSet.getString("organization.id"));
                    }
                }
            }
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
        sc.close();
    }
}
