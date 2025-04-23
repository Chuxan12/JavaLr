package com.example.dao;

import com.example.model.User;
import javax.sql.DataSource;
import java.sql.*;

public class UserDao {
    private final DataSource ds;
    public UserDao(DataSource ds){ this.ds = ds; }

    public boolean register(User u) {
        String sql = "INSERT INTO users(login,nickname,password_hash) VALUES(?,?,?)";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, u.getLogin());
            ps.setString(2, u.getNickname());
            ps.setString(3, u.getPasswordHash());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false; // duplicate or other error
        }
    }

    public User findByLogin(String login) {
        String sql = "SELECT login,nickname,password_hash FROM users WHERE login=?";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getString(1), rs.getString(2), rs.getString(3));
                }
            }
        } catch (SQLException ignored) {}
        return null;
    }
}