package main.customer;

/** The stages in a customer's visit to the restaurant. */
public enum CustomerState {
    QUEUING,
    SEATED,
    WAITING_FOR_FOOD,
    EATING,
    COMPLETED,
    READY_TO_PAY,
    PAID,
    LEFT
}
