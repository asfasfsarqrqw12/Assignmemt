package Repositories.jdbc;

import Entities.ClassBooking;
import Repositories.ClassBookingRepository;
import edu.aitu.oop3.db.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JdbcClassBookingRepository implements ClassBookingRepository {

    @Override
    public void create(long memberId, long classId) throws SQLException {
        String sql = "INSERT INTO class_bookings (member_id, class_id) VALUES (?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, memberId);
            ps.setLong(2, classId);
            ps.executeUpdate();

        } catch (SQLException e) {
            // 23505 = unique_violation in Postgres (member_id, class_id unique)
            if ("23505".equals(e.getSQLState())) {
                throw new Exceptions.BookingAlreadyExistsException("Booking already exists");
            }
            throw e;
        }
    }

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
    public int countBookingsForClass(long classId) throws SQLException {
        String sql = "SELECT COUNT(*) AS c FROM class_bookings WHERE class_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, classId);

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt("c");
            }
        }
    }

    @Override
    public List<ClassBooking> findByMemberId(long memberId) throws SQLException {
        String sql = "SELECT id, member_id, class_id, booked_at FROM class_bookings WHERE member_id = ? ORDER BY id";

        List<ClassBooking> list = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, memberId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ClassBooking b = new ClassBooking();
                    b.setId(rs.getLong("id"));
                    b.setMemberId(rs.getLong("member_id"));
                    b.setClassId(rs.getLong("class_id"));

                    Timestamp ts = rs.getTimestamp("booked_at");
                    b.setBookedAt(ts == null ? null : ts.toLocalDateTime());

                    list.add(b);
                }
            }
        }
        return list;
    }
}
