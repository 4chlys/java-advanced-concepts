package be.kdg.model;

import java.math.BigDecimal;

public abstract class Vehicle {
    protected Color color;
    protected double weight;
    protected BigDecimal price;
    protected Currency currency;

    public Vehicle(Color color, double weight, BigDecimal price, Currency currency) {
        this.color = color;
        this.weight = weight;
        this.price = price;
        this.currency = currency;
    }

    /**
     * Gets the primary color of this vehicle.
     *
     * @return the color enum value
     * @see Color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Gets the total weight of this vehicle in kilograms.
     *
     * @return the weight in kg as a double
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Gets the retail price of this vehicle.
     *
     * @return the price as a BigDecimal, may be null if not set
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Gets the currency in which the price is denominated.
     *
     * @return the currency enum value
     * @see Currency
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * Sets the primary color of this vehicle.
     *
     * @param color the color to set
     * @see Color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Sets the total weight of this vehicle.
     *
     * @param weight the weight in kilograms (must be non-negative)
     * @throws IllegalArgumentException if weight is negative
     */
    public void setWeight(double weight) {
        if (weight < 0.0) {
            throw new IllegalArgumentException("weight cannot be negative");
        }
        this.weight = weight;
    }

    /**
     * Sets the retail price of this vehicle.
     *
     * @param price the price to set (must not be negative if not null)
     * @throws IllegalArgumentException if price is negative
     */
    public void setPrice(BigDecimal price) {
        if (price != null && price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("price cannot be negative");
        }
        this.price = price;
    }

    /**
     * Sets the currency in which the price is denominated.
     *
     * @param currency the currency to set
     * @see Currency
     */
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return String.format("colour: %-8s (%.0f %s) %.1f kg",
                color.toString(),
                price != null ? price : BigDecimal.ZERO,
                currency.toString(),
                weight);
    }
}
