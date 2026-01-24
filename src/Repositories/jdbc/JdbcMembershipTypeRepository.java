package Repositories.jdbc;

import Entities.MembershipType;
import Repositories.MembershipTypeRepository;
import edu.aitu.oop3.db.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
