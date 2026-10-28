package main.menu;

import java.util.Objects;

/**
 * One dish with a fixed name, price (integer cents) and number of preparation units.
 * <p>
 * Immutable value object: all fields are final and set once in the constructor.
 */
public class MenuItem {

    private final String name;
    private final int priceCents;
    private final int prepUnits;

    /**
     * @param name       display name, e.g. "Salad"
     * @param priceCents price in cents, e.g. 900 for $9.00
     * @param prepUnits  preparation units the Chef must complete (at least 1)
     */
    public MenuItem(String name, int priceCents, int prepUnits) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Menu item name cannot be empty");
        }
        if (priceCents < 0) {
            throw new IllegalArgumentException("Price cannot be negative: " + priceCents);
        }
        if (prepUnits < 1) {
            throw new IllegalArgumentException("A dish needs at least 1 preparation unit: " + prepUnits);
        }
        this.name = name;
        this.priceCents = priceCents;
        this.prepUnits = prepUnits;
    }

    public String getName() {
        return name;
    }

    public int getPriceCents() {
        return priceCents;
    }

    public int getPrepUnits() {
        return prepUnits;
    }

    /** e.g. "Pasta ($18.00, 2 units)". */
    @Override
    public String toString() {
        return String.format("%s (%s, %d unit%s)",
                name, Money.format(priceCents), prepUnits, prepUnits == 1 ? "" : "s");
    }

    /**
     * Value equality. getClass() is compared so a subclass is never equal to a
     * plain MenuItem that happens to share its name, price and units.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MenuItem other = (MenuItem) o;
        return priceCents == other.priceCents
                && prepUnits == other.prepUnits
                && name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, priceCents, prepUnits);
    }
}
