package be.kdg.model;

import java.util.*;
import java.util.function.Function;

public class RaceBikes {
    private Set<RaceBike> raceBikes;

    public RaceBikes() {
        this.raceBikes = new TreeSet<>();
    }

    public RaceBikes(Collection<RaceBike> raceBikes) {
        this.raceBikes = new TreeSet<>(raceBikes);
    }

    /**
     * Adds a RaceBike to the collection.
     * Returns true if the bike was added, false if it already exists.
     */
    public boolean add(RaceBike raceBike) {
        if (raceBike == null) {
            throw new IllegalArgumentException("RaceBike cannot be null");
        }
        return raceBikes.add(raceBike);
    }

    /**
     * Adds multiple RaceBikes to the collection.
     * @param raceBikes
     * @return true if any bike was added, false if all bikes already existed or raceBikes was null.
     */
    public boolean addAll(Collection<RaceBike> raceBikes) {
        if (raceBikes == null) {
            throw new IllegalArgumentException("RaceBikes cannot be null");
        }
        return this.raceBikes.addAll(raceBikes);
    }

    /**
     * Removes a RaceBike from the collection using an Iterator (safe removal).
     * Returns true if the bike was removed, false if it wasn't found.
     */
    public boolean remove(RaceBike raceBike) {
        if (raceBike == null) {
            return false;
        }

        Iterator<RaceBike> iterator = raceBikes.iterator();
        while (iterator.hasNext()) {
            RaceBike bike = iterator.next();
            if (bike.equals(raceBike)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a RaceBike by brand and model using an Iterator.
     * Returns true if the bike was removed, false if it wasn't found.
     */
    public boolean remove(Brand brand, String model) {
        if (brand == null || model == null || model.trim().isEmpty()) {
            return false;
        }

        Iterator<RaceBike> iterator = raceBikes.iterator();
        while (iterator.hasNext()) {
            RaceBike bike = iterator.next();
            if (bike.getBrand() == brand && bike.getModel().equalsIgnoreCase(model.trim())) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    /**
     * Removes all RaceBikes that match a given predicate.
     * Returns the number of bikes removed.
     *
     * Example usage:
     * - collection.removeIf(bike -> bike.getBrand() == Brand.TREK)
     * - collection.removeIf(bike -> bike.getPrice().compareTo(new BigDecimal("5000")) > 0)
     */
    public int removeIf(java.util.function.Predicate<RaceBike> predicate) {
        if (predicate == null) {
            throw new IllegalArgumentException("Predicate cannot be null");
        }

        int initialSize = raceBikes.size();
        raceBikes.removeIf(predicate);
        return initialSize - raceBikes.size();
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
        RaceBike searchBike = new RaceBike(brand, model);
        for (RaceBike bike : raceBikes) {
            if (bike.equals(searchBike)) {
                return bike;
            }
        }
        return null;
    }

    /**
     * Returns all RaceBikes as a sorted list (by natural ordering).
     */
    public List<RaceBike> getAllRaceBikes() {
        return new ArrayList<>(raceBikes);
    }

    /**
     * Returns all RaceBikes as a sorted list (by Brands).
     */
    public List<RaceBike> sortedBy(Function<RaceBike, Comparable> function) {
        List<RaceBike> sortedList = new ArrayList<>(raceBikes);
        sortedList.sort(Comparator.comparing(function));
        return sortedList;
    }

    /**
     * Returns all RaceBikes of a specific brand.
     */
    public List<RaceBike> getRaceBikesByBrand(Brand brand) {
        return raceBikes.stream()
                .filter(bike -> bike.getBrand() == brand)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
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