package ru.job4j.tracker.actions;

import ru.job4j.tracker.tracker.ITracker;
import ru.job4j.tracker.input.Input;

import java.util.function.Consumer;

public class ExitAction implements UserAction {
    @Override
    public String name() {
        return "=== Exit ====";
    }

    @Override
    public boolean execute(Input input, Consumer<String> output, ITracker tracker) {
        return false;
    }
}
