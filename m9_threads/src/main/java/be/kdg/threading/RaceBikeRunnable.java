package be.kdg.threading;

import be.kdg.model.RaceBike;
import be.kdg.model.RaceBikeFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class RaceBikeRunnable implements Runnable {
    private final Predicate<RaceBike> predicate;
    private List<RaceBike> filteredBikes;
    private static final int TOTAL_BIKES = 1000;

    /**
     * Constructor that accepts a Predicate to filter RaceBikes
     * @param predicate The filter condition to apply on generated bikes
     */
    public RaceBikeRunnable(Predicate<RaceBike> predicate) {
        if (predicate == null) {
            throw new IllegalArgumentException("Predicate cannot be null");
        }
        this.predicate = predicate;
        this.filteredBikes = new ArrayList<>();
    }

    /**
     * Generates exactly 1000 random RaceBikes that match the predicate
     */
    @Override
    public void run() {
        filteredBikes = java.util.stream.Stream.generate(RaceBikeFactory::newRandomRaceBike)
                .filter(predicate)
                .limit(TOTAL_BIKES)
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Returns the list of filtered bikes
     * @return List of RaceBikes that passed the filter
     */
    public List<RaceBike> getFilteredBikes() {
        return new ArrayList<>(filteredBikes);
    }

    /**
     * Returns the count of filtered bikes
     * @return Number of bikes that passed the filter
     */
    public int getFilteredCount() {
        return filteredBikes.size();
    }
}