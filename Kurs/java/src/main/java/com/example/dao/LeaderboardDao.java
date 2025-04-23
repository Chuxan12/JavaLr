package com.example.dao;

import com.example.model.LeaderboardEntry;

import javax.sql.DataSource;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Сохраняет счёт игрока и отдаёт TOP-N.
 * База – таблица {@code scores(nickname, score, played_at TIMESTAMP)}.
 */
public class LeaderboardDao {

    private final DataSource ds;

    public LeaderboardDao(DataSource ds) {
        this.ds = ds;
    }

    /** Добавляем запись о партии */
    public void add(LeaderboardEntry e) {
        String sql = "INSERT INTO scores(nickname, score, played_at) VALUES (?,?,?)";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString   (1, e.getNickname());
            ps.setInt      (2, e.getScore());
            ps.setTimestamp(3, Timestamp.from(e.getPlayedAt().toInstant())); // Date → Timestamp
            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();          // или логгер
        }
    }

    /** Возвращаем TOP-{@code n} по убыванию счёта */
    public List<LeaderboardEntry> top(int n) {
        String sql = """
                     SELECT nickname, score, played_at
                       FROM scores
                      ORDER BY score DESC
                      LIMIT ?
                     """;

        List<LeaderboardEntry> list = new ArrayList<>();

        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, n);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();          // или логгер
        }
        return list;
    }

    /** Маппер строки ResultSet → LeaderboardEntry */
    private static LeaderboardEntry map(ResultSet rs) throws SQLException {
        return new LeaderboardEntry(
                rs.getString("nickname"),
                rs.getInt("score"),
                rs.getTimestamp("played_at").toInstant()        // Timestamp → Instant
        );
    }
}
