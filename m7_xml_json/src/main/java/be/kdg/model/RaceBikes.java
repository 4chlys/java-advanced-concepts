package be.kdg.model;

import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessType;


import java.util.*;
import java.util.function.Predicate;

@XmlRootElement(name = "race-bikes")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class RaceBikes {
    private List<RaceBike> raceBikeList;

    public RaceBikes() {
        this.raceBikeList = new ArrayList<>();
    }

    /**
     * Adds a RaceBike to the list.
     */
    public boolean add(RaceBike raceBike) {
        if (raceBike == null) {
            throw new IllegalArgumentException("RaceBike cannot be null");
        }
        return raceBikeList.add(raceBike);
    }

    /**
     * Removes a RaceBike using safe iterator removal.
     */
    public boolean remove(RaceBike raceBike) {
        if (raceBike == null) return false;

        Iterator<RaceBike> iterator = raceBikeList.iterator();
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
     * Removes a RaceBike by brand and model.
     */
    public boolean remove(Brand brand, String model) {
        if (brand == null || model == null || model.trim().isEmpty()) return false;

        Iterator<RaceBike> iterator = raceBikeList.iterator();
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
     * Removes bikes matching a predicate.
     */
    public int removeIf(Predicate<RaceBike> predicate) {
        if (predicate == null) throw new IllegalArgumentException("Predicate cannot be null");

        int initialSize = raceBikeList.size();
        raceBikeList.removeIf(predicate);
        return initialSize - raceBikeList.size();
    }

    /**
     * Checks if a RaceBike with brand & model exists.
     */
    public boolean contains(Brand brand, String model) {
        RaceBike searchBike = new RaceBike(brand, model);
        return raceBikeList.contains(searchBike);
    }

    /**
     * Finds a RaceBike by brand and model.
     */
    public RaceBike search(Brand brand, String model) {
        RaceBike searchBike = new RaceBike(brand, model);
        for (RaceBike bike : raceBikeList) {
            if (bike.equals(searchBike)) {
                return bike;
            }
        }
        return null;
    }

    /**
     * JAXB getter.
     */
    @XmlElement(name = "race-bike")
    public List<RaceBike> getRaceBikeList() {
        return raceBikeList;
    }

    /**
     * JAXB setter.
     */
    public void setRaceBikeList(List<RaceBike> raceBikeList) {
        this.raceBikeList = raceBikeList;
    }

    /**
     * Sorted views of the list.
     */
    public List<RaceBike> sortedOnBrand() {
        List<RaceBike> list = new ArrayList<>(raceBikeList);
        list.sort(Comparator.comparing(RaceBike::getBrand));
        return list;
    }

    public List<RaceBike> sortedOnModel() {
        List<RaceBike> list = new ArrayList<>(raceBikeList);
        list.sort(Comparator.comparing(RaceBike::getModel));
        return list;
    }

    public List<RaceBike> sortedOnColor() {
        List<RaceBike> list = new ArrayList<>(raceBikeList);
        list.sort(Comparator.comparing(RaceBike::getColor));
        return list;
    }

    public List<RaceBike> sortedOnPrice() {
        List<RaceBike> list = new ArrayList<>(raceBikeList);
        list.sort(Comparator.comparing(RaceBike::getPrice));
        return list;
    }

    public List<RaceBike> sortedOnFrameMaterial() {
        List<RaceBike> list = new ArrayList<>(raceBikeList);
        list.sort(Comparator.comparing(RaceBike::getFrameMaterial));
        return list;
    }

    /**
     * Filter by brand.
     */
    public List<RaceBike> getRaceBikesByBrand(Brand brand) {
        return raceBikeList.stream()
                .filter(bike -> bike.getBrand() == brand)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public int getSize() {
        return raceBikeList.size();
    }

    public boolean isEmpty() {
        return raceBikeList.isEmpty();
    }

    public void clear() {
        raceBikeList.clear();
    }

    @Override
    public String toString() {
        return "RaceBikes{" + raceBikeList.size() + " bikes: " + raceBikeList + "}";
    }
}
