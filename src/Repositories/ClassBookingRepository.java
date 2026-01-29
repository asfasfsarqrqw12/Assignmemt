package Repositories;

import Entities.ClassBooking;
import java.sql.SQLException;
import java.util.List;

public interface ClassBookingRepository {
    void create(long memberId, long classId) throws SQLException;

    boolean exists(long memberId, long classId) throws SQLException;

    int countBookingsForClass(long classId) throws SQLException;

    List<ClassBooking> findByMemberId(long memberId) throws SQLException;
}
