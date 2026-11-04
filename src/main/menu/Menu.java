package main.menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Registry of every dish the restaurant offers: the base items it is given
 * and the combos built from them.
 */
public class Menu {

    private final List<MenuItem> baseItems = new ArrayList<>();
    private final List<ComboMeal> combos = new ArrayList<>();

    /**
     * Creates the menu: the given base items in order (Salad, Burger, Pasta in every game
     * setting so far), then the Salad + Pasta combo.
     *
     * @throws IllegalArgumentException if the list is null or empty, or has no Salad or no Pasta
     */
    public Menu(List<? extends MenuItem> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("A menu needs at least one base item");
        }
        baseItems.addAll(items);
        // Combo rule: customers whose id is divisible by 3 order from getCombos()
        combos.add(new ComboMeal(getItemByName("Salad"), getItemByName("Pasta")));
    }

    /** Read-only view of the base items, in registration order. */
    public List<MenuItem> getBaseItems() {
        return Collections.unmodifiableList(baseItems);
    }

    /** Read-only view of the combos, in registration order. */
    public List<ComboMeal> getCombos() {
        return Collections.unmodifiableList(combos);
    }

    /** Read-only copy of every dish: base items first, then combos. */
    public List<MenuItem> getAllItems() {
        List<MenuItem> all = new ArrayList<>(baseItems);
        all.addAll(combos);
        return Collections.unmodifiableList(all);
    }

    /**
     * Adds a combo to the menu.
     *
     * @throws IllegalArgumentException if the combo is null or a dish with the same name
     *         (case-insensitive) is already on the menu
     */
    public void addCombo(ComboMeal combo) {
        if (combo == null) {
            throw new IllegalArgumentException("Cannot add a null combo");
        }
        for (MenuItem item : getAllItems()) {
            if (item.getName().equalsIgnoreCase(combo.getName())) {
                throw new IllegalArgumentException("Menu already has an item named \"" + combo.getName() + "\"");
            }
        }
        combos.add(combo);
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
        MenuItem best = baseItems.get(0);   // never empty: the constructor rejects an empty list
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
