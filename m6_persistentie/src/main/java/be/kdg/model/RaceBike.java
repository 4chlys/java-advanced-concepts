package be.kdg.model;

import java.beans.Transient;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.Objects;

public class RaceBike implements Comparable<RaceBike>, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private int id;

    private Brand brand;
    private String model;
    private Color color;

    private double weight;
    private int frameSize;

    private LocalDate releaseDate;

    private BigDecimal price;
    private Currency currency;

    private Groupset groupset;
    private transient FrameMaterial frameMaterial;
    private transient ForkMaterial forkMaterial;
    private transient BrakeType brakeType;
    private transient WheelSize wheelSize;

    public RaceBike(int id, Brand brand, String model, Color color, double weight, int frameSize, LocalDate releaseDate, BigDecimal price, Currency currency, Groupset groupset, FrameMaterial frameMaterial, ForkMaterial forkMaterial, BrakeType brakeType, WheelSize wheelSize) {
        this.id = id;
        setBrand(brand);
        setModel(model);
        this.color = color;
        setWeight(weight);
        setFrameSize(frameSize);
        setReleaseDate(releaseDate);
        setPrice(price);
        this.currency = currency;
        this.groupset = groupset;
        this.frameMaterial = frameMaterial;
        this.forkMaterial = forkMaterial;
        this.brakeType = brakeType;
        this.wheelSize = wheelSize;
    }

    public RaceBike(Brand brand, String model, Color color, double weight, int frameSize, LocalDate releaseDate, BigDecimal price, Currency currency, Groupset groupset, FrameMaterial frameMaterial, ForkMaterial forkMaterial, BrakeType brakeType, WheelSize wheelSize) {
        this(-1, brand, model, color, weight, frameSize, releaseDate, price, currency, groupset, frameMaterial, forkMaterial, brakeType, wheelSize);
    }

    public RaceBike(Brand brand, String model, Color color) {
        this(-1, brand, model, color, 0.0, 0, LocalDate.now(), BigDecimal.ZERO, Currency.EUR, null, null, null, null, null);
    }

    public RaceBike(Brand brand, String model) {
        this(-1, brand, model, Color.WHITE, 0.0, 0, LocalDate.now(), BigDecimal.ZERO, Currency.EUR, null, null, null, null, null);
    }

    public RaceBike() {
        this(-1, Brand.UNKNOWN, "Unknown", Color.WHITE, 0.0, 0, LocalDate.now(), BigDecimal.ZERO, Currency.EUR, null, null, null, null, null);
    }

    // Getters
    public int getId() {
        return id;
    }

    public Brand getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public Color getColor() {
        return color;
    }

    public double getWeight() {
        return weight;
    }

    public int getFrameSize() {
        return frameSize;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Groupset getGroupset() {
        return groupset;
    }

    public FrameMaterial getFrameMaterial() {
        return frameMaterial;
    }

    public ForkMaterial getForkMaterial() {
        return forkMaterial;
    }

    public BrakeType getBrakeType() {
        return brakeType;
    }

    public WheelSize getWheelSize() {
        return wheelSize;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setBrand(Brand brand) {
        if (brand == null) {
            throw new IllegalArgumentException("brand cannot be null");
        }
        if (!EnumSet.allOf(Brand.class).contains(brand)) {
            throw new IllegalArgumentException("brand is not a valid brand");
        }
        this.brand = brand;
    }

    public void setModel(String model) {
        if (model == null || model.trim().isEmpty()) {
            throw new IllegalArgumentException("model cannot be null or empty");
        }
        this.model = model.trim();
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setWeight(double weight) {
        if (weight < 0.0) {
            throw new IllegalArgumentException("weight cannot be negative");
        }
        this.weight = weight;
    }

    public void setFrameSize(int frameSize) {
        if (frameSize < 0) {
            throw new IllegalArgumentException("frameSize cannot be negative");
        }
        this.frameSize = frameSize;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        if (releaseDate == null) {
            throw new IllegalArgumentException("releaseDate cannot be null");
        }
        this.releaseDate = releaseDate;
    }

    public void setPrice(BigDecimal price) {
        if (price != null && price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("price cannot be negative");
        }
        this.price = price;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setGroupset(Groupset groupset) {
        this.groupset = groupset;
    }

    public void setFrameMaterial(FrameMaterial frameMaterial) {
        this.frameMaterial = frameMaterial;
    }

    public void setForkMaterial(ForkMaterial forkMaterial) {
        this.forkMaterial = forkMaterial;
    }

    public void setBrakeType(BrakeType brakeType) {
        this.brakeType = brakeType;
    }

    public void setWheelSize(WheelSize wheelSize) {
        this.wheelSize = wheelSize;
    }

    /**
     * Ensures uniqueness based on Brand and Model combination.
     * Two RaceBikes are considered equal if they have the same Brand and Model.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        RaceBike raceBike = (RaceBike) obj;
        return brand == raceBike.brand &&
                Objects.equals(model, raceBike.model);
    }

    /**
     * Hash code based on Brand and Model to ensure consistency with equals.
     */
    @Override
    public int hashCode() {
        return Objects.hash(brand, model);
    }

    /**
     * Natural ordering: First by Brand (alphabetically), then by Model (alphabetically).
     * This provides a consistent sorting order for RaceBike objects.
     */
    @Override
    public int compareTo(RaceBike other) {
        if (other == null) {
            throw new NullPointerException("Cannot compare to null RaceBike");
        }

        int brandComparison = this.brand.compareTo(other.brand);
        if (brandComparison != 0) {
            return brandComparison;
        }

        return this.model.compareToIgnoreCase(other.model);
    }

    @Override
    public String toString() {
        return String.format("%-12s %-20s colour: %-8s (%.0f %s) %-8s frame: %-10s %.1f kg",
                brand.toString(),
                model,
                color.toString(),
                price != null ? price : BigDecimal.ZERO,
                currency.toString(),
                frameMaterial != null ? frameMaterial.toString() : "N/A",
                frameSize > 0 ? frameSize + "cm" : "N/A",
                weight);
    }
}