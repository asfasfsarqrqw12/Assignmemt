package Repositories.jdbc;

import Entities.Member;
import edu.aitu.oop3.db.DatabaseConnection;
import repositories.MemberRepository;

import java.sql.*;
import java.time.LocalDate;

public class JdbcMemberRepository implements MemberRepository {

    @Override
    public Member findById(long id) throws SQLException {
        String sql = "SELECT id, full_name, phone, membership_type_id, membership_end_date " +
                "FROM members WHERE id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                Member m = new Member();
                m.setId(rs.getLong("id"));
                m.setFullName(rs.getString("full_name"));
                m.setPhone(rs.getString("phone"));
                m.setMembershipTypeId(rs.getLong("membership_type_id"));

                Date d = rs.getDate("membership_end_date");
                m.setMembershipEndDate(d == null ? null : d.toLocalDate());

                return m;
            }
        }
    }

    @Override
    public void updateMembership(long memberId, long membershipTypeId, LocalDate endDate) throws SQLException {
        String sql = "UPDATE members SET membership_type_id = ?, membership_end_date = ? WHERE id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, membershipTypeId);

            if (endDate == null) ps.setNull(2, Types.DATE);
            else ps.setDate(2, Date.valueOf(endDate));

            ps.setLong(3, memberId);
            ps.executeUpdate();
        }
    }
}