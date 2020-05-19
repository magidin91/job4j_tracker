package ru.job4j.tracker.input;

/**
 * The interface receives input
 */
public interface Input {

    /**
     * Returns the input received from the user
     */
    String askStr(String question);

    /**
     * Converts the input received from the user to the number of a menu item
     */
    int askInt(String question);

    /**
     * Converts input data received from the user to a menu number based on the number of menu items
     */
    int askInt(String question, int max);
}