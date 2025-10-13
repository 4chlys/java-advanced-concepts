package be.kdg.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.logging.Level;

public class RaceBike implements Comparable<RaceBike> {
    // 3.1 Voorzie in de basisklasse een extra Logger-attribuut. In alle setters waar je een IllegalArgumentException
    //gooit als de meegegeven parameter foutief is, VERVANG je de throw door een logging te doen met niveau
    //SEVERE. De boodschap moet (naast tekst) ook de foute waarde en de naam van het object bevatten.
    //Bijvoorbeeld: “Geboortedatum 2078-12-18 ligt niet in het verleden voor Jozef Stalin”
    private static final Logger logger = Logger.getLogger(RaceBike.class.getName());

    private Brand brand;
    private String model;
    private Color color;

    private double weight;
    private int frameSize;

    private LocalDate releaseDate;

    private BigDecimal price;
    private Currency currency;

    private Groupset groupset;
    private FrameMaterial frameMaterial;
    private ForkMaterial forkMaterial;
    private BrakeType brakeType;
    private WheelSize wheelSize;

    public RaceBike(Brand brand, String model, Color color, double weight, int frameSize, LocalDate releaseDate, BigDecimal price, Currency currency, Groupset groupset, FrameMaterial frameMaterial, ForkMaterial forkMaterial, BrakeType brakeType, WheelSize wheelSize) {
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

    public RaceBike(Brand brand, String model, Color color) {
        this(brand, model, color, 0.0, 0, LocalDate.now(), BigDecimal.ZERO, Currency.EUR, null, null, null, null, null);
    }

    public RaceBike(Brand brand, String model) {
        this(brand, model, Color.WHITE, 0.0, 0, LocalDate.now(), BigDecimal.ZERO, Currency.EUR, null, null, null, null, null);
    }

    public RaceBike() {
        this(Brand.UNKNOWN, "Unknown", Color.WHITE, 0.0, 0, LocalDate.now(), BigDecimal.ZERO, Currency.EUR, null, null, null, null, null);
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

    public void setBrand(Brand brand) {
        if (brand == null) {
            logger.log(Level.SEVERE, String.format("Brand cannot be null for %s %s",
                    this.brand != null ? this.brand : "Unknown",
                    this.model != null ? this.model : "Unknown"));
            return;
        }
        if (!EnumSet.allOf(Brand.class).contains(brand)) {
            logger.log(Level.SEVERE, String.format("Brand %s is not valid for %s %s",
                    brand, this.brand, this.model));
            return;
        }
        this.brand = brand;
    }

    public void setModel(String model) {
        if (model == null || model.trim().isEmpty()) {
            logger.log(Level.SEVERE, String.format("Model cannot be null or empty for %s %s",
                    this.brand, this.model != null ? this.model : "Unknown"));
            return;
        }
        this.model = model.trim();
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setWeight(double weight) {
        if (weight < 0.0) {
            logger.log(Level.SEVERE, String.format("Weight %.2f cannot be negative for %s %s",
                    weight, this.brand, this.model));
            return;
        }
        this.weight = weight;
    }

    public void setFrameSize(int frameSize) {
        if (frameSize < 0) {
            logger.log(Level.SEVERE, String.format("FrameSize %d cannot be negative for %s %s",
                    frameSize, this.brand, this.model));
            return;
        }
        this.frameSize = frameSize;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        if (releaseDate == null) {
            logger.log(Level.SEVERE, String.format("ReleaseDate cannot be null for %s %s",
                    this.brand, this.model));
            return;
        }
        if (releaseDate.isAfter(LocalDate.now())) {
            logger.log(Level.SEVERE, String.format("ReleaseDate %s is in the future for %s %s",
                    releaseDate, this.brand, this.model));
            return;
        }
        this.releaseDate = releaseDate;
    }

    public void setPrice(BigDecimal price) {
        if (price != null && price.compareTo(BigDecimal.ZERO) < 0) {
            logger.log(Level.SEVERE, String.format("Price %s cannot be negative for %s %s",
                    price, this.brand, this.model));
            return;
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