package be.kdg.persist;

import be.kdg.model.RaceBike;
import be.kdg.model.RaceBikes;

import java.util.List;

public interface RaceBikeDao {
    boolean insert(RaceBike raceBike);
    boolean update(RaceBike raceBike);
    boolean delete(String model);
    RaceBike retrieve(String model);
    List<RaceBike> sortedOn(String query);
}
