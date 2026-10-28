package main.menu;

/**
 * Small utility for money held as integer cents (brief, Section 2.2).
 * <p>
 * Every percentage in the game goes through {@link #percentOf(int, int)} so the
 * whole team rounds the same way, using whole-number arithmetic only (no
 * floating-point error).
 * <p>
 * Not in the original class diagram: added so every percentage shares one
 * rounding rule.
 */
public final class Money {

    private Money() {
        // utility class: no instances
    }

    /**
     * Returns {@code percent}% of {@code cents}, rounded to the nearest cent
     * (halves round up).
     * <p>
     * Examples: percentOf(2700, 90) = 2430; percentOf(1500, 90) = 1350;
     * percentOf(1350, 80) = 1080.
     *
     * @param cents   a non-negative amount in cents
     * @param percent a non-negative percentage (e.g. 90 for "90% of")
     */
    public static int percentOf(int cents, int percent) {
        if (cents < 0) {
            throw new IllegalArgumentException("Amount cannot be negative: " + cents);
        }
        if (percent < 0) {
            throw new IllegalArgumentException("Percentage cannot be negative: " + percent);
        }
        long scaled = (long) cents * percent;   // long avoids overflow on large totals
        return (int) ((scaled + 50) / 100);     // +50 then integer-divide = round half up
    }

    /** Formats cents as dollars with two decimals, e.g. 2430 becomes "$24.30". */
    public static String format(int cents) {
        String sign = cents < 0 ? "-" : "";
        int abs = Math.abs(cents);
        return String.format("%s$%d.%02d", sign, abs / 100, abs % 100);
    }
}
