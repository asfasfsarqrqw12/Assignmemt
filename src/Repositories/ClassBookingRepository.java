package Repositories;

import java.sql.SQLException;
import java.util.List;

public interface ClassBookingRepository {
    boolean exists(long memberId, long classId) throws SQLException;
    int countByClassId(long classId) throws SQLException;
    void insert(long memberId, long classId) throws SQLException;


    List<String> historyLines(long memberId) throws SQLException;
}
