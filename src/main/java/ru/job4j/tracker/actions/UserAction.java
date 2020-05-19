package ru.job4j.tracker.actions;

import ru.job4j.tracker.tracker.ITracker;
import ru.job4j.tracker.input.Input;

import java.util.function.Consumer;

/**
 * The interface describes the functionality of the menu item
 */
public interface UserAction {

    /**
     * Returns the name of the menu item
     */
    String name();

    /**
     * Performs the action of the menu item
     */
    boolean execute(Input input, Consumer<String> output, ITracker tracker);
}