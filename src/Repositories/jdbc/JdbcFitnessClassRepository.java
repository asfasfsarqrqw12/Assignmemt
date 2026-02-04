package Repositories.jdbc;

import Entities.FitnessClass;
import edu.aitu.oop3.db.DatabaseConnection;
import Repositories.FitnessClassRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcFitnessClassRepository implements FitnessClassRepository {

    @Override
    public FitnessClass findById(long id) throws SQLException {
        String sql = "SELECT id, title, coach_name, start_time, capacity FROM fitness_classes WHERE id = ?";

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


    @Override
    public List<FitnessClass> findAll() throws SQLException {
        String sql = "SELECT id, title, coach_name, start_time, capacity FROM fitness_classes ORDER BY start_time";
        List<FitnessClass> classes = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                FitnessClass c = new FitnessClass();
                c.setId(rs.getLong("id"));
                c.setTitle(rs.getString("title"));
                c.setCoachName(rs.getString("coach_name"));

                Timestamp ts = rs.getTimestamp("start_time");
                c.setStartTime(ts == null ? null : ts.toLocalDateTime());

                c.setCapacity(rs.getInt("capacity"));
                classes.add(c);
            }
        }
        return classes;
    }
}