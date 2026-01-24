package Repositories.jdbc;

import Entities.FitnessClass;
import edu.aitu.oop3.db.DatabaseConnection;
import Repositories.FitnessClassRepository;

import java.sql.*;
import java.time.LocalDateTime;

public class JdbcFitnessClassRepository implements FitnessClassRepository {

    @Override
    public FitnessClass findById(long id) throws SQLException {
        String sql = "SELECT id, title, coach_name, start_time, capacity FROM classes WHERE id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                FitnessClass c = new FitnessClass();
                c.setId(rs.getLong("id"));
                c.setTitle(rs.getString("title"));
                c.setCoachName(rs.getString("coach_name"));

                Timestamp ts = rs.getTimestamp("start_time");
                c.setStartTime(ts == null ? null : ts.toLocalDateTime());

                c.setCapacity(rs.getInt("capacity"));
                return c;
            }
        }
    }
}