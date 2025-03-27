package java.v1;

import java.sql.*;
import java.util.Scanner;

public class DatabaseManager {
    private final Connection connection;

    public DatabaseManager(String url, String user, String password) throws SQLException {
        this.connection = DriverManager.getConnection(url, user, password);
    }

    public void displayOrdersWithCustomers() throws SQLException {
        String sql = "SELECT o.order_id, o.order_date, c.customer_id, c.name, c.email " +
                     "FROM orders o JOIN customers c ON o.customer_id = c.customer_id";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.printf("Order ID: %d, Date: %s, Customer: %s (%s)%n",
                        rs.getInt("order_id"),
                        rs.getDate("order_date"),
                        rs.getString("name"),
                        rs.getString("email"));
            }
        }
    }

    public void addCustomer(String name, String email) throws SQLException {
        String sql = "INSERT INTO customers (name, email) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
        }
    }

    public void addOrder(int customerId, Date orderDate) throws SQLException {
        String sql = "INSERT INTO orders (customer_id, order_date) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, customerId);
            pstmt.setDate(2, orderDate);
            pstmt.executeUpdate();
        }
    }

    public boolean deleteCustomer(int customerId) throws SQLException {
        if (hasOrders(customerId)) {
            System.out.print("У клиента есть заказы. Удалить их? (yes/no): ");
            Scanner scanner = new Scanner(System.in);
            if (!scanner.nextLine().equalsIgnoreCase("yes")) {
                return false;
            }
            deleteOrdersForCustomer(customerId);
        }
        deleteCustomerWithoutCheck(customerId);
        return true;
    }

    private boolean hasOrders(int customerId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM orders WHERE customer_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    private void deleteOrdersForCustomer(int customerId) throws SQLException {
        String sql = "DELETE FROM orders WHERE customer_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, customerId);
            pstmt.executeUpdate();
        }
    }

    private void deleteCustomerWithoutCheck(int customerId) throws SQLException {
        String sql = "DELETE FROM customers WHERE customer_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, customerId);
            pstmt.executeUpdate();
        }
    }

    public void deleteOrder(int orderId) throws SQLException {
        String sql = "DELETE FROM orders WHERE order_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, orderId);
            pstmt.executeUpdate();
        }
    }
}