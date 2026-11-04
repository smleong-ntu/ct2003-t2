package main.menu;

/**
 * STAGE 3 ONLY. A combo made of two existing (single) menu items.
 * <ul>
 *   <li>Preparation units = sum of the two components' units.</li>
 *   <li>Price = 90% of the components' combined price, rounded to the nearest cent.
 *       Example: Salad + Pasta = round((900 + 1800) x 0.90) = 2430 cents, 3 units.</li>
 * </ul>
 * Because a ComboMeal IS-A MenuItem, Order, Chef and Cashier handle it with no changes.
 */
public class ComboMeal extends MenuItem {

    /** Combo price is this percentage of the components' combined price. */
    public static final int COMBO_PRICE_PERCENT = 90;

    private final MenuItem componentA;
    private final MenuItem componentB;

    public ComboMeal(MenuItem componentA, MenuItem componentB) {
        // Java runs super(...) before any other statement, so the helpers below
        // must be static and must validate their own arguments.
        super(buildName(componentA, componentB),
              comboPrice(componentA, componentB),
              requireComponent(componentA).getPrepUnits() + requireComponent(componentB).getPrepUnits());
        this.componentA = componentA;
        this.componentB = componentB;
    }

    public MenuItem getComponentA() {
        return componentA;
    }

    public MenuItem getComponentB() {
        return componentB;
    }

    /** e.g. "Salad + Pasta". */
    public String getComponentNames() {
        return componentA.getName() + " + " + componentB.getName();
    }

    // ---- static helpers used inside the super(...) call ----

    private static String buildName(MenuItem a, MenuItem b) {
        return requireComponent(a).getName() + " + " + requireComponent(b).getName() + " Combo";
    }

    private static int comboPrice(MenuItem a, MenuItem b) {
        int combined = requireComponent(a).getPriceCents() + requireComponent(b).getPriceCents();
        return Money.percentOf(combined, COMBO_PRICE_PERCENT);
    }

    /**
     * The brief says a combo is made of two existing menu items, so a component
     * must be a plain item and not another combo.
     */
    private static MenuItem requireComponent(MenuItem item) {
        if (item == null) {
            throw new IllegalArgumentException("A combo component cannot be null");
        }
        if (item instanceof ComboMeal) {
            throw new IllegalArgumentException("A combo cannot contain another combo: " + item.getName());
        }
        return item;
    }
}
