package Repositories.jdbc;

import edu.aitu.oop3.db.DatabaseConnection;
import Repositories.ClassBookingRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcClassBookingRepository implements ClassBookingRepository {

    @Override
    public boolean exists(long memberId, long classId) throws SQLException {
        String sql = "SELECT 1 FROM class_bookings WHERE member_id = ? AND class_id = ? LIMIT 1";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, memberId);
            ps.setLong(2, classId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    @Override
    public int countByClassId(long classId) throws SQLException {
        String sql = "SELECT COUNT(*) AS cnt FROM class_bookings WHERE class_id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, classId);

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt("cnt");
            }
        }
    }

    @Override
    public void insert(long memberId, long classId) throws SQLException {
        String sql = "INSERT INTO class_bookings(member_id, class_id) VALUES (?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, memberId);
            ps.setLong(2, classId);
            ps.executeUpdate();
        }
    }

    @Override
    public List<String> historyLines(long memberId) throws SQLException {
        String sql = """
            SELECT cb.id AS booking_id, c.title, c.coach_name, c.start_time, cb.booked_at
            FROM class_bookings cb
            JOIN classes c ON c.id = cb.class_id
            WHERE cb.member_id = ?
            ORDER BY cb.booked_at
        """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, memberId);

            try (ResultSet rs = ps.executeQuery()) {
                List<String> lines = new ArrayList<>();
                while (rs.next()) {
                    long bookingId = rs.getLong("booking_id");
                    String title = rs.getString("title");
                    String coach = rs.getString("coach_name");
                    Timestamp st = rs.getTimestamp("start_time");
                    Timestamp bt = rs.getTimestamp("booked_at");

                    lines.add("booking#" + bookingId +
                            " | " + title +
                            " | coach=" + coach +
                            " | start=" + (st == null ? "null" : st.toLocalDateTime()) +
                            " | booked=" + (bt == null ? "null" : bt.toLocalDateTime()));
                }
                return lines;
            }
        }
    }
}