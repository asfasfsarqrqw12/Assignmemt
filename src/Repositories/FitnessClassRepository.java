package Repositories;

import Entities.FitnessClass;
import java.sql.SQLException;

public interface FitnessClassRepository {
    FitnessClass findById(long id) throws SQLException;
}
