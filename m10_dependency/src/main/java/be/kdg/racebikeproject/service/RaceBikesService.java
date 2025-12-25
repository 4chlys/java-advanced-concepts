package be.kdg.racebikeproject.service;

import be.kdg.racebikeproject.model.RaceBike;

import java.util.List;

public interface RaceBikesService {
    List<RaceBike> getAllRaceBikes();
    boolean addRaceBike(RaceBike raceBike);
    boolean updateRaceBike(RaceBike raceBike);
    boolean deleteRaceBike(RaceBike raceBike);
}
