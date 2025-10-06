package be.kdg.model;

import be.kdg.reflection.CanRun;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.Objects;

/**
 * Represents a racing bicycle with comprehensive specifications and characteristics.
 * This class models professional and amateur racing bikes with detailed technical information
 * including brand, model, materials, components, and pricing.
 *
 * <p>Race bikes are uniquely identified by the combination of their brand and model.
 * Two bikes with the same brand and model are considered equal, regardless of other attributes.
 * The class implements natural ordering by brand first, then by model name.</p>
 *
 * <p>All monetary values are stored as BigDecimal for precision, and the class provides
 * comprehensive validation for all input parameters to ensure data integrity.</p>
 *
 * @author Romeo Weyns
 * @version 1.2
 * @see <a href="https://en.wikipedia.org/wiki/Racing_bicycle">Racing bicycle - Wikipedia</a>
 * @since 1.0
 */
public class RaceBike extends Vehicle implements Comparable<RaceBike> {
    private Brand brand;
    private String model;

    private int frameSize;

    private LocalDate releaseDate;

    private Groupset groupset;
    private FrameMaterial frameMaterial;
    private ForkMaterial forkMaterial;
    private BrakeType brakeType;
    private WheelSize wheelSize;

    /**
     * Creates a fully specified RaceBike with all technical details.
     * This constructor allows for complete initialization of all bicycle specifications.
     *
     * @param brand the manufacturer brand (must not be null)
     * @param model the specific model name (must not be null or empty)
     * @param color the primary frame color
     * @param weight the total weight in kilograms (must be non-negative)
     * @param frameSize the frame size in centimeters (must be non-negative)
     * @param releaseDate the official release date (must not be null)
     * @param price the retail price (must not be negative)
     * @param currency the price currency
     * @param groupset the drivetrain groupset
     * @param frameMaterial the frame material composition
     * @param forkMaterial the fork material composition
     * @param brakeType the braking system type
     * @param wheelSize the wheel and tire size specification
     * @throws IllegalArgumentException if any validation constraint is violated
     */
    public RaceBike(Brand brand, String model, Color color, double weight, int frameSize, LocalDate releaseDate, BigDecimal price, Currency currency, Groupset groupset, FrameMaterial frameMaterial, ForkMaterial forkMaterial, BrakeType brakeType, WheelSize wheelSize) {
        super(color, weight, price, currency);
        setBrand(brand);
        setModel(model);
        setFrameSize(frameSize);
        setReleaseDate(releaseDate);
        this.groupset = groupset;
        this.frameMaterial = frameMaterial;
        this.forkMaterial = forkMaterial;
        this.brakeType = brakeType;
        this.wheelSize = wheelSize;
    }

    /**
     * Creates a RaceBike with basic specifications (brand, model, color).
     * Other technical specifications are set to default values and can be updated later.
     *
     * @param brand the manufacturer brand (must not be null)
     * @param model the specific model name (must not be null or empty)
     * @param color the primary frame color
     * @throws IllegalArgumentException if brand is null or model is null/empty
     */
    public RaceBike(Brand brand, String model, Color color) {
        this(brand, model, color, 0.0, 0, LocalDate.now(), BigDecimal.ZERO, Currency.EUR, null, null, null, null, null);
    }

    /**
     * Creates a RaceBike with minimal specifications (brand and model only).
     * The color defaults to WHITE, and other specifications are set to default values.
     *
     * @param brand the manufacturer brand (must not be null)
     * @param model the specific model name (must not be null or empty)
     * @throws IllegalArgumentException if brand is null or model is null/empty
     */
    public RaceBike(Brand brand, String model) {
        this(brand, model, Color.WHITE, 0.0, 0, LocalDate.now(), BigDecimal.ZERO, Currency.EUR, null, null, null, null, null);
    }

    /**
     * Creates a default RaceBike with unknown brand and model.
     * This constructor is primarily used for creating search templates
     * or when bike details are to be filled in later.
     */
    public RaceBike() {
        this(Brand.UNKNOWN, "Unknown", Color.WHITE, 0.0, 0, LocalDate.now(), BigDecimal.ZERO, Currency.EUR, null, null, null, null, null);
    }

    // Getters
    /**
     * Gets the manufacturer brand of this racing bicycle.
     *
     * @return the brand enum value
     * @see Brand
     */
    public Brand getBrand() {
        return brand;
    }

    /**
     * Gets the specific model name of this racing bicycle.
     *
     * @return the model name as a string
     */
    public String getModel() {
        return model;
    }

    /**
     * Gets the frame size of this racing bicycle in centimeters.
     *
     * @return the frame size in cm as an integer
     */
    public int getFrameSize() {
        return frameSize;
    }

    /**
     * Gets the official release date of this bicycle model.
     *
     * @return the release date as a LocalDate
     */
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    /**
     * Gets the drivetrain groupset of this racing bicycle.
     *
     * @return the groupset enum value, may be null if not specified
     * @see Groupset
     */
    public Groupset getGroupset() {
        return groupset;
    }

    /**
     * Gets the material composition of the main frame.
     *
     * @return the frame material enum value, may be null if not specified
     * @see FrameMaterial
     */
    public FrameMaterial getFrameMaterial() {
        return frameMaterial;
    }

    /**
     * Gets the material composition of the front fork.
     *
     * @return the fork material enum value, may be null if not specified
     * @see ForkMaterial
     */
    public ForkMaterial getForkMaterial() {
        return forkMaterial;
    }

    /**
     * Gets the type of braking system used on this bicycle.
     *
     * @return the brake type enum value, may be null if not specified
     * @see BrakeType
     */
    public BrakeType getBrakeType() {
        return brakeType;
    }

    /**
     * Gets the wheel and tire size specification of this bicycle.
     *
     * @return the wheel size enum value, may be null if not specified
     * @see WheelSize
     */
    public WheelSize getWheelSize() {
        return wheelSize;
    }

    // Setters
    /**
     * Sets the manufacturer brand of this racing bicycle.
     *
     * @param brand the brand to set (must not be null)
     * @throws IllegalArgumentException if brand is null or not a valid Brand enum value
     * @see Brand
     */
    public void setBrand(Brand brand) {
        if (brand == null) {
            throw new IllegalArgumentException("brand cannot be null");
        }
        if (!EnumSet.allOf(Brand.class).contains(brand)) {
            throw new IllegalArgumentException("brand is not a valid brand");
        }
        this.brand = brand;
    }

    /**
     * Sets the specific model name of this racing bicycle.
     *
     * @param model the model name to set (must not be null or empty)
     * @throws IllegalArgumentException if model is null or empty after trimming
     */
    @CanRun("Dogma F12")
    public void setModel(String model) {
        if (model == null || model.trim().isEmpty()) {
            throw new IllegalArgumentException("model cannot be null or empty");
        }
        this.model = model.trim();
    }

    /**
     * Sets the frame size of this racing bicycle.
     *
     * @param frameSize the frame size in centimeters (must be non-negative)
     * @throws IllegalArgumentException if frameSize is negative
     */
    public void setFrameSize(int frameSize) {
        if (frameSize < 0) {
            throw new IllegalArgumentException("frameSize cannot be negative");
        }
        this.frameSize = frameSize;
    }

    /**
     * Sets the official release date of this bicycle model.
     *
     * @param releaseDate the release date to set (must not be null)
     * @throws IllegalArgumentException if releaseDate is null
     */
    public void setReleaseDate(LocalDate releaseDate) {
        if (releaseDate == null) {
            throw new IllegalArgumentException("releaseDate cannot be null");
        }
        this.releaseDate = releaseDate;
    }

    /**
     * Sets the drivetrain groupset of this racing bicycle.
     *
     * @param groupset the groupset to set
     * @see Groupset
     */
    public void setGroupset(Groupset groupset) {
        this.groupset = groupset;
    }

    /**
     * Sets the material composition of the main frame.
     *
     * @param frameMaterial the frame material to set
     * @see FrameMaterial
     */
    public void setFrameMaterial(FrameMaterial frameMaterial) {
        this.frameMaterial = frameMaterial;
    }

    /**
     * Sets the material composition of the front fork.
     *
     * @param forkMaterial the fork material to set
     * @see ForkMaterial
     */
    public void setForkMaterial(ForkMaterial forkMaterial) {
        this.forkMaterial = forkMaterial;
    }

    /**
     * Sets the type of braking system used on this bicycle.
     *
     * @param brakeType the brake type to set
     * @see BrakeType
     */
    public void setBrakeType(BrakeType brakeType) {
        this.brakeType = brakeType;
    }

    /**
     * Sets the wheel and tire size specification of this bicycle.
     *
     * @param wheelSize the wheel size to set
     * @see WheelSize
     */
    public void setWheelSize(WheelSize wheelSize) {
        this.wheelSize = wheelSize;
    }

    /**
     * Ensures uniqueness based on Brand and Model combination.
     * Two RaceBikes are considered equal if they have the same Brand and Model.
     *
     * @return boolean
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
     *
     * @return hashCode
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
        return String.format("%-12s %-20s %s %-8s frame: %-10s",
                brand.toString(),
                model,
                super.toString(),
                frameMaterial != null ? frameMaterial.toString() : "N/A",
                frameSize > 0 ? frameSize + "cm" : "N/A");
    }
}