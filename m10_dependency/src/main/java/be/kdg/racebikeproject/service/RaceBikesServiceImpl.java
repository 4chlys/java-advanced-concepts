package be.kdg.racebikeproject.service;

import be.kdg.racebikeproject.database.RaceBikeDao;
import be.kdg.racebikeproject.database.RaceBikeDbDao;
import be.kdg.racebikeproject.model.RaceBike;

import java.util.List;

public class RaceBikesServiceImpl implements RaceBikesService{
    private RaceBikeDao dao;

    public RaceBikesServiceImpl(RaceBikeDao dao){
        this.dao = dao;
    }

    @Override
    public List<RaceBike> getAllRaceBikes() {
        return dao.getAll();
    }

    @Override
    public boolean addRaceBike(RaceBike raceBike) {
        return dao.insert(raceBike);
    }

    @Override
    public boolean updateRaceBike(RaceBike raceBike) {
        return dao.update(raceBike);
    }

    @Override
    public boolean deleteRaceBike(RaceBike raceBike) {
        return dao.delete(raceBike.getModel());
    }
}
