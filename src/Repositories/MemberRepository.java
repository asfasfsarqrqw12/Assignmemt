package Repositories;

import Entities.Member;
import java.sql.SQLException;
import java.time.LocalDate;

public interface MemberRepository {
    Member findById(long id) throws SQLException;
    void updateMembership(long memberId, long membershipTypeId, LocalDate endDate) throws SQLException;
}
