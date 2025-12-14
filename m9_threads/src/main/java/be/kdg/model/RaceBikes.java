package be.kdg.model;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

/**
 * 4.4 - Thread-safe version using ArrayBlockingQueue
 */
public class RaceBikes {
    private BlockingQueue<RaceBike> raceBikes;

    /**
     * Default constructor with capacity of 10000
     */
    public RaceBikes() {
        this(10000);
    }

    /**
     * 4.4.b - Constructor with capacity parameter
     * @param capacity The maximum capacity of the queue
     */
    public RaceBikes(int capacity) {
        this.raceBikes = new ArrayBlockingQueue<>(capacity);
    }

    /**
     * Adds a RaceBike to the collection.
     * Returns true if the bike was added, false if queue is full or bike is null.
     */
    public boolean add(RaceBike raceBike) {
        if (raceBike == null) {
            throw new IllegalArgumentException("RaceBike cannot be null");
        }
        return raceBikes.offer(raceBike);
    }

    /**
     * Removes a RaceBike from the collection.
     * Returns true if the bike was removed, false if it wasn't found.
     */
    public boolean remove(RaceBike raceBike) {
        if (raceBike == null) {
            return false;
        }
        return raceBikes.remove(raceBike);
    }

    /**
     * Removes a RaceBike by brand and model.
     * Returns true if the bike was removed, false if it wasn't found.
     */
    public boolean remove(Brand brand, String model) {
        if (brand == null || model == null || model.trim().isEmpty()) {
            return false;
        }

        RaceBike toRemove = raceBikes.stream()
                .filter(bike -> bike.getBrand() == brand &&
                        bike.getModel().equalsIgnoreCase(model.trim()))
                .findFirst()
                .orElse(null);

        return toRemove != null && raceBikes.remove(toRemove);
    }

    /**
     * Removes all RaceBikes that match a given predicate.
     * Returns the number of bikes removed.
     */
    public int removeIf(java.util.function.Predicate<RaceBike> predicate) {
        if (predicate == null) {
            throw new IllegalArgumentException("Predicate cannot be null");
        }

        List<RaceBike> toRemove = raceBikes.stream()
                .filter(predicate)
                .toList();

        int count = 0;
        for (RaceBike bike : toRemove) {
            if (raceBikes.remove(bike)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Checks if a RaceBike with the given brand and model exists.
     */
    public boolean contains(Brand brand, String model) {
        RaceBike searchBike = new RaceBike(brand, model);
        return raceBikes.contains(searchBike);
    }

    /**
     * Finds a RaceBike by brand and model.
     * Returns the RaceBike if found, null otherwise.
     */
    public RaceBike search(Brand brand, String model) {
        return raceBikes.stream()
                .filter(bike -> bike.getBrand() == brand &&
                        bike.getModel().equalsIgnoreCase(model))
                .findFirst()
                .orElse(null);
    }

    /**
     * Returns all RaceBikes as a list.
     */
    public List<RaceBike> getAllRaceBikes() {
        return new ArrayList<>(raceBikes);
    }

    /**
     * Returns all RaceBikes sorted by Brand.
     */
    public List<RaceBike> sortedOnBrand() {
        return raceBikes.stream()
                .sorted(Comparator.comparing(RaceBike::getBrand))
                .collect(Collectors.toList());
    }

    /**
     * Returns all RaceBikes sorted by Model.
     */
    public List<RaceBike> sortedOnModel() {
        return raceBikes.stream()
                .sorted(Comparator.comparing(RaceBike::getModel))
                .collect(Collectors.toList());
    }

    /**
     * Returns all RaceBikes sorted by Color.
     */
    public List<RaceBike> sortedOnColor() {
        return raceBikes.stream()
                .sorted(Comparator.comparing(RaceBike::getColor))
                .collect(Collectors.toList());
    }

    /**
     * Returns all RaceBikes sorted by Price.
     */
    public List<RaceBike> sortedOnPrice() {
        return raceBikes.stream()
                .sorted(Comparator.comparing(RaceBike::getPrice))
                .collect(Collectors.toList());
    }

    /**
     * Returns all RaceBikes sorted by Frame Material.
     */
    public List<RaceBike> sortedOnFrameMaterial() {
        return raceBikes.stream()
                .sorted(Comparator.comparing(RaceBike::getFrameMaterial))
                .collect(Collectors.toList());
    }

    /**
     * Returns all RaceBikes of a specific brand.
     */
    public List<RaceBike> getRaceBikesByBrand(Brand brand) {
        return raceBikes.stream()
                .filter(bike -> bike.getBrand() == brand)
                .collect(Collectors.toList());
    }

    /**
     * Returns the number of RaceBikes in the collection.
     */
    public int getSize() {
        return raceBikes.size();
    }

    /**
     * Checks if the collection is empty.
     */
    public boolean isEmpty() {
        return raceBikes.isEmpty();
    }

    /**
     * Clears all RaceBikes from the collection.
     */
    public void clear() {
        raceBikes.clear();
    }

    @Override
    public String toString() {
        return "RaceBikes{" + raceBikes.size() + " bikes: " + raceBikes + "}";
    }
}