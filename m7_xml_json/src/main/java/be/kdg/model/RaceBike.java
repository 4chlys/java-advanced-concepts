package be.kdg.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.Objects;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = {"model", "color", "weight", "frameSize", "releaseDate",
        "price", "currency", "groupset", "frameMaterial",
        "forkMaterial", "brakeType", "wheelSize"})
public class RaceBike implements Comparable<RaceBike> {
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

    @XmlAttribute(name = "brand")
    public Brand getBrand() {
        return brand;
    }

    @XmlElement
    public String getModel() {
        return model;
    }

    @XmlElement
    public Color getColor() {
        return color;
    }

    @XmlElement
    public double getWeight() {
        return weight;
    }

    @XmlElement(name = "frame-size")
    public int getFrameSize() {
        return frameSize;
    }

    @XmlElement(name = "release-date")
    @XmlJavaTypeAdapter(be.kdg.parsing.LocalDateXmlAdapter.class)
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    @XmlElement
    @XmlJavaTypeAdapter(be.kdg.parsing.BigDecimalXmlAdapter.class)
    public BigDecimal getPrice() {
        return price;
    }

    @XmlElement
    public Currency getCurrency() {
        return currency;
    }

    @XmlElement
    public Groupset getGroupset() {
        return groupset;
    }

    @XmlElement(name = "frame-material")
    public FrameMaterial getFrameMaterial() {
        return frameMaterial;
    }

    @XmlElement(name = "fork-material")
    public ForkMaterial getForkMaterial() {
        return forkMaterial;
    }

    @XmlElement(name = "brake-type")
    public BrakeType getBrakeType() {
        return brakeType;
    }

    @XmlElement(name = "wheel-size")
    public WheelSize getWheelSize() {
        return wheelSize;
    }

    public void setBrand(Brand brand) {
        if (brand == null || !EnumSet.allOf(Brand.class).contains(brand)) {
            throw new IllegalArgumentException("Invalid brand");
        }
        this.brand = brand;
    }

    public void setModel(String model) {
        if (model == null || model.isBlank()) throw new IllegalArgumentException("model cannot be null");
        this.model = model.trim();
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setWeight(double weight) {
        if (weight < 0.0) throw new IllegalArgumentException("weight cannot be negative");
        this.weight = weight;
    }

    public void setFrameSize(int frameSize) {
        if (frameSize < 0) throw new IllegalArgumentException("frameSize cannot be negative");
        this.frameSize = frameSize;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        if (releaseDate == null) throw new IllegalArgumentException("releaseDate cannot be null");
        this.releaseDate = releaseDate;
    }

    public void setPrice(BigDecimal price) {
        if (price != null && price.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("price cannot be negative");
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