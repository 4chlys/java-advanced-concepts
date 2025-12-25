package be.kdg.racebikeproject.database;

import be.kdg.racebikeproject.model.RaceBike;

import java.util.List;

public interface RaceBikeDao {
    boolean insert(RaceBike raceBike);
    boolean update(RaceBike raceBike);
    boolean delete(String model);
    RaceBike retrieve(String model);
    List<RaceBike> getAll();
    List<RaceBike> sortedOn(String query);
}
