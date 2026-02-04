package Repositories;

import Entities.FitnessClass;
import java.sql.SQLException;
import java.util.List;

public interface FitnessClassRepository {
    FitnessClass findById(long id) throws SQLException;
    List<FitnessClass> findAll() throws SQLException;
}
