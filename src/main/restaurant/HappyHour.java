package main.restaurant;

import main.menu.Money;

/**
 * Once-per-game pricing policy the manager can switch on at the start of a turn.
 * Lasts that turn and the next. Orders taken while active pay 80% of their price.
 */
public class HappyHour {
    public static final int DURATION_TURNS = 2;
    public static final int PRICE_PERCENT = 80;
    public static final int RECOVERY_POINTS = 15;

    private int activatedTurn = -1;

    public void activate(int turn) {
        if (turn < 1) {
            throw new IllegalArgumentException("Turn numbers start at 1: " + turn);
        }
        if (hasBeenUsed()) {
            throw new IllegalStateException("Happy Hour has already been used this game");
        }
        activatedTurn = turn;
    }

    public boolean hasBeenUsed() {
        return activatedTurn != -1;
    }

    public boolean isActive(int turn) {
        return hasBeenUsed() && turn >= activatedTurn && turn < activatedTurn + DURATION_TURNS;
    }

    public int remainingTurns(int turn) {
        return isActive(turn) ? activatedTurn + DURATION_TURNS - turn : 0;
    }

    /** Returns the discounted price. Does not check {@link #isActive}: the caller does. */
    public int applyTo(int cents) {
        return Money.percentOf(cents, PRICE_PERCENT);
    }
}
