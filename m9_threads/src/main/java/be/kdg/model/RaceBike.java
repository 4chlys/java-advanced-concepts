package be.kdg.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public final class RaceBike implements Comparable<RaceBike> {
    private final Brand brand;
    private final String model;
    private final Color color;
    private final double weight;
    private final int frameSize;
    private final LocalDate releaseDate;
    private final BigDecimal price;
    private final Currency currency;
    private final Groupset groupset;
    private final FrameMaterial frameMaterial;
    private final ForkMaterial forkMaterial;
    private final BrakeType brakeType;
    private final WheelSize wheelSize;

    public RaceBike(Brand brand, String model, Color color, double weight, int frameSize,
                    LocalDate releaseDate, BigDecimal price, Currency currency, Groupset groupset,
                    FrameMaterial frameMaterial, ForkMaterial forkMaterial, BrakeType brakeType,
                    WheelSize wheelSize) {
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.weight = weight;
        this.frameSize = frameSize;
        this.releaseDate = releaseDate;
        this.price = price;
        this.currency = currency;
        this.groupset = groupset;
        this.frameMaterial = frameMaterial;
        this.forkMaterial = forkMaterial;
        this.brakeType = brakeType;
        this.wheelSize = wheelSize;
    }

    public RaceBike(Brand brand, String model, Color color) {
        this(brand, model, color, 0.0, 0, LocalDate.now(), BigDecimal.ZERO, Currency.EUR,
                null, null, null, null, null);
    }

    public RaceBike(Brand brand, String model) {
        this(brand, model, Color.WHITE, 0.0, 0, LocalDate.now(), BigDecimal.ZERO, Currency.EUR,
                null, null, null, null, null);
    }

    public RaceBike() {
        this(Brand.UNKNOWN, "Unknown", Color.WHITE, 0.0, 0, LocalDate.now(), BigDecimal.ZERO,
                Currency.EUR, null, null, null, null, null);
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


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        RaceBike raceBike = (RaceBike) obj;
        return brand == raceBike.brand &&
                Objects.equals(model, raceBike.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, model);
    }

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