package components.membership;

import Entities.MembershipType;
import java.sql.SQLException;

public interface MembershipTypeRepository {
    MembershipType findById(long id) throws SQLException;
}
