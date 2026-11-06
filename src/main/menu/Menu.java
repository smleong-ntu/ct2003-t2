package main.menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Registry of every dish the restaurant offers: base items and combos, both given by the
 * game setting. Combos are kept apart, so rules on base items never pick a combo.
 */
public class Menu {

    private final List<MenuItem> baseItems = new ArrayList<>();
    private final List<ComboMeal> combos = new ArrayList<>();

    /**
     * Creates the menu from the setting's items, keeping their order. Each {@link ComboMeal}
     * goes to the combos, every other item to the base items.
     *
     * @throws IllegalArgumentException if the list is null or has no base item, two dishes
     *         share a name (case-insensitive), or a combo uses a dish that is not a base item
     */
    public Menu(List<? extends MenuItem> items) {
        if (items == null) {
            throw new IllegalArgumentException("A menu needs at least one base item");
        }
        for (MenuItem item : items) {
            if (item instanceof ComboMeal combo) {
                combos.add(requireNewName(combo));
            } else {
                baseItems.add(requireNewName(item));
            }
        }
        if (baseItems.isEmpty()) {
            throw new IllegalArgumentException("A menu needs at least one base item");
        }
        for (ComboMeal combo : combos) {
            requireBaseItem(combo, combo.getComponentA());
            requireBaseItem(combo, combo.getComponentB());
        }
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
        combos.add(requireNewName(combo));
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

    private <T extends MenuItem> T requireNewName(T item) {
        if (item == null) {
            throw new IllegalArgumentException("A menu item cannot be null");
        }
        for (MenuItem existing : getAllItems()) {
            if (existing.getName().equalsIgnoreCase(item.getName())) {
                throw new IllegalArgumentException("Menu already has an item named \"" + item.getName() + "\"");
            }
        }
        return item;
    }

    // The brief: a combo is made of two existing menu items
    private void requireBaseItem(ComboMeal combo, MenuItem component) {
        for (MenuItem item : baseItems) {
            if (item.getName().equalsIgnoreCase(component.getName())) {
                return;
            }
        }
        throw new IllegalArgumentException(
                "\"" + combo.getName() + "\" uses \"" + component.getName() + "\", which is not on the menu");
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
