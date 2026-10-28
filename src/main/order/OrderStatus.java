package main.order;

/**
 * Lifecycle of an Order.
 * <pre>
 * PLACED -> IN_PREPARATION -> READY -> SERVED -> PAID
 *    \___________\______________\
 *                                 -> CANCELLED  (customer left before being served)
 * </pre>
 * PAID and CANCELLED are not in the original diagram. The brief requires both:
 * payment "marks the customer and order PAID" (Section 3.2, step 4), and
 * abandonment must "cancel any unfinished order" (step 5).
 */
public enum OrderStatus {
    PLACED,
    IN_PREPARATION,
    READY,
    SERVED,
    PAID,
    CANCELLED
}
