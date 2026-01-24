package Repositories.jdbc;

import Entities.MembershipType;
import edu.aitu.oop3.db.DatabaseConnection;
import repositories.MembershipTypeRepository;

import java.sql.*;
import java.math.BigDecimal;

public class JdbcMembershipTypeRepository implements MembershipTypeRepository {

    @Override
    public MembershipType findById(long id) throws SQLException {
        String sql = "SELECT id, name, duration_days, price FROM membership_types WHERE id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                MembershipType t = new MembershipType();
                t.setId(rs.getLong("id"));
                t.setName(rs.getString("name"));

                int dur = rs.getInt("duration_days");
                t.setDurationDays(rs.wasNull() ? null : dur);

                BigDecimal price = rs.getBigDecimal("price");
                t.setPrice(price);

                return t;
            }
        }
    }
}