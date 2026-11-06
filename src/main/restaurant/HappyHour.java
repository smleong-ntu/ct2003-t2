package main.restaurant;

import java.util.function.IntConsumer;

import main.menu.Money;

/**
 * Once-per-game pricing policy the manager can switch on at the start of a turn.
 * Lasts that turn and the next. Orders taken while active pay 80% of their price, and
 * customers still waiting recover {@link #RECOVERY_POINTS} at the start of each active turn.
 * <p>
 * It keeps its own countdown, so it needs no turn numbers. Each turn start calls {@link #tick()}:
 * <pre>
 * turn 1: activate()  -> recovery, 2 turns left
 * turn 2: tick()      -> 1 turn left, still active, recovery
 * turn 3: tick()      -> 0 turns left, switches off, no recovery
 * </pre>
 */
public class HappyHour {
    public static final int DURATION_TURNS = 2;
    public static final int PRICE_PERCENT = 80;
    public static final int RECOVERY_POINTS = 15;

    private final IntConsumer recovery;
    private Runnable updateHandle = () -> {};
    private boolean used;
    private int remainingTurns;

    /** @param recovery applies the given satisfaction recovery to the waiting customers */
    public HappyHour(IntConsumer recovery) {
        if (recovery == null) {
            throw new IllegalArgumentException("Happy Hour needs a recovery action");
        }
        this.recovery = recovery;
    }

    /** Runs whenever Happy Hour switches on, counts down or switches off. */
    public void onUpdate(Runnable handle) {
        updateHandle = handle == null ? () -> {} : handle;
    }

    /**
     * Switches Happy Hour on for this turn and the next.
     *
     * @throws IllegalStateException if it has already been used this game
     */
    public void activate() {
        if (used) {
            throw new IllegalStateException("Happy Hour has already been used this game");
        }
        used = true;
        remainingTurns = DURATION_TURNS;
        onActivated();
    }

    /** One turn start: counts down, then recovers if still active. Does nothing while off. */
    public void tick() {
        if (!isActive()) {
            return;
        }
        remainingTurns--;
        if (remainingTurns == 0) {
            onDeactivated();
            return;
        }
        recovery.accept(RECOVERY_POINTS);
        updateHandle.run();
    }

    public boolean hasBeenUsed() {
        return used;
    }

    public boolean isActive() {
        return remainingTurns > 0;
    }

    /** Active turns left, counting the current one; 0 when off. */
    public int remainingTurns() {
        return remainingTurns;
    }

    /** Returns the discounted price. Does not check {@link #isActive}: the caller does. */
    public int applyTo(int cents) {
        return Money.percentOf(cents, PRICE_PERCENT);
    }

    // The activation turn recovers too, because activate() runs after that turn's tick().
    private void onActivated() {
        recovery.accept(RECOVERY_POINTS);
        updateHandle.run();
    }

    private void onDeactivated() {
        updateHandle.run();
    }
}
