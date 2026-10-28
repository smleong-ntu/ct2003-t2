package main.menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Registry of every dish the restaurant offers.
 * The constructor registers the three base items.
 */
public class Menu {

    private final List<MenuItem> baseItems = new ArrayList<>();

    /** Creates the base menu: Salad ($9.00, 1), Burger ($15.00, 1), Pasta ($18.00, 2). */
    public Menu() {
        baseItems.add(new MenuItem("Salad", 900, 1));
        baseItems.add(new MenuItem("Burger", 1500, 1));
        baseItems.add(new MenuItem("Pasta", 1800, 2));
    }

    /** Read-only view of the base items, in registration order. */
    public List<MenuItem> getBaseItems() {
        return Collections.unmodifiableList(baseItems);
    }

    /** Read-only copy of every dish, in registration order (base items first). */
    public List<MenuItem> getAllItems() {
        return Collections.unmodifiableList(new ArrayList<>(baseItems));
    }

    /**
     * Looks up a dish by name (case-insensitive).
     *
     * @throws IllegalArgumentException if no dish has that name, with a message
     *         explaining why, instead of returning null
     */
    public MenuItem getItemByName(String name) {
        if (name != null) {
            for (MenuItem item : getAllItems()) {
                if (item.getName().equalsIgnoreCase(name.trim())) {
                    return item;
                }
            }
        }
        throw new IllegalArgumentException("No menu item named \"" + name + "\"");
    }

    /**
     * Returns the highest-priced base item. On a tie, the one registered first wins,
     * which keeps any rule built on it deterministic.
     */
    public MenuItem getMostExpensive() {
        MenuItem best = baseItems.get(0);   // never empty: the constructor adds 3 items
        for (MenuItem item : baseItems) {
            if (item.getPriceCents() > best.getPriceCents()) {
                best = item;
            }
        }
        return best;
    }

    public int size() {
        return getAllItems().size();
    }

    @Override
    public String toString() {
        List<MenuItem> all = getAllItems();
        StringBuilder sb = new StringBuilder("Menu:");
        for (int i = 0; i < all.size(); i++) {
            sb.append(System.lineSeparator()).append("  ").append(i + 1).append(". ").append(all.get(i));
        }
        return sb.toString();
    }
}
