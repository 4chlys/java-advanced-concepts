package be.kdg.model;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.logging.Level;

public class RaceBikes {
    // 3.3 Voeg in de multiklasse logging van niveau FINER toe die registreert welke objecten toegevoegd worden
    //aan of verwijderd uit de TreeSet.
    private static final Logger logger = Logger.getLogger(RaceBikes.class.getName());

    private Set<RaceBike> raceBikes;

    public RaceBikes() {
        this.raceBikes = new TreeSet<>();
    }

    public RaceBikes(Collection<RaceBike> raceBikes) {
        this.raceBikes = new TreeSet<>(raceBikes);
    }

    public boolean add(RaceBike raceBike) {
        if (raceBike == null) {
            throw new IllegalArgumentException("RaceBike cannot be null");
        }
        boolean added = raceBikes.add(raceBike);
        if (added) {
            logger.log(Level.FINER, String.format("Added RaceBike: %s %s",
                    raceBike.getBrand(), raceBike.getModel()));
        }
        return added;
    }

    public boolean addAll(Collection<RaceBike> raceBikes) {
        if (raceBikes == null) {
            throw new IllegalArgumentException("RaceBikes cannot be null");
        }
        boolean modified = false;
        for (RaceBike bike : raceBikes) {
            if (add(bike)) {
                modified = true;
            }
        }
        return modified;
    }

    public boolean remove(RaceBike raceBike) {
        if (raceBike == null) {
            return false;
        }
        boolean removed = raceBikes.removeIf(bike -> bike.equals(raceBike));
        if (removed) {
            logger.log(Level.FINER, String.format("Removed RaceBike: %s %s",
                    raceBike.getBrand(), raceBike.getModel()));
        }
        return removed;
    }

    public boolean remove(Brand brand, String model) {
        if (brand == null || model == null || model.trim().isEmpty()) {
            return false;
        }

        boolean removed = raceBikes.removeIf(bike -> {
            boolean matches = bike.getBrand() == brand &&
                    bike.getModel().equalsIgnoreCase(model.trim());
            if (matches) {
                logger.log(Level.FINER, String.format("Removed RaceBike: %s %s",
                        bike.getBrand(), bike.getModel()));
            }
            return matches;
        });

        return removed;
    }

    public int removeIf(Predicate<RaceBike> predicate) {
        if (predicate == null) {
            throw new IllegalArgumentException("Predicate cannot be null");
        }

        int initialSize = raceBikes.size();

        raceBikes.removeIf(bike -> {
            boolean matches = predicate.test(bike);
            if (matches) {
                logger.log(Level.FINER, String.format("Removed RaceBike: %s %s",
                        bike.getBrand(), bike.getModel()));
            }
            return matches;
        });

        return initialSize - raceBikes.size();
    }

    public boolean contains(Brand brand, String model) {
        RaceBike searchBike = new RaceBike(brand, model);
        return raceBikes.contains(searchBike);
    }

    public RaceBike search(Brand brand, String model) {
        RaceBike searchBike = new RaceBike(brand, model);
        return raceBikes.stream()
                .filter(bike -> bike.equals(searchBike))
                .findFirst()
                .orElse(null);
    }

    public List<RaceBike> getAllRaceBikes() {
        return new ArrayList<>(raceBikes);
    }

    public List<RaceBike> sortedBy(Function<RaceBike, Comparable> function) {
        List<RaceBike> sortedList = new ArrayList<>(raceBikes);
        sortedList.sort(Comparator.comparing(function));
        return sortedList;
    }

    public List<RaceBike> getRaceBikesByBrand(Brand brand) {
        return raceBikes.stream()
                .filter(bike -> bike.getBrand() == brand)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public int getSize() {
        return raceBikes.size();
    }

    public boolean isEmpty() {
        return raceBikes.isEmpty();
    }

    public void clear() {
        raceBikes.clear();
    }

    @Override
    public String toString() {
        return "RaceBikes{" + raceBikes.size() + " bikes: " + raceBikes + "}";
    }
}