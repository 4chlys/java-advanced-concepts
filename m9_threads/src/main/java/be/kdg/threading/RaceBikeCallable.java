package be.kdg.threading;

import be.kdg.model.RaceBike;
import be.kdg.model.RaceBikeFactory;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RaceBikeCallable implements Callable<List<RaceBike>> {
    private final Predicate<RaceBike> predicate;
    private static final int TOTAL_BIKES = 1000;

    /**
     * Constructor that accepts a Predicate to filter RaceBikes
     * @param predicate The filter condition to apply on generated bikes
     */
    public RaceBikeCallable(Predicate<RaceBike> predicate) {
        if (predicate == null) {
            throw new IllegalArgumentException("Predicate cannot be null");
        }
        this.predicate = predicate;
    }

    /**
     * Generates exactly 1000 random RaceBikes that match the predicate
     * @return List of RaceBikes matching the predicate
     */
    @Override
    public List<RaceBike> call() {
        return Stream.generate(RaceBikeFactory::newRandomRaceBike)
                .filter(predicate)
                .limit(TOTAL_BIKES)
                .collect(Collectors.toList());
    }
}