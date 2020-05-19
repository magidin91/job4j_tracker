package ru.job4j.tracker.actions;

import ru.job4j.tracker.tracker.ITracker;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.model.Item;

import java.util.function.Consumer;

public class ShowAllAction implements UserAction {
    @Override
    public String name() {
        return "=== Show all ====";
    }

    @Override
    public boolean execute(Input input, Consumer<String> output, ITracker tracker) {
        output.accept("=== Done ====");
        for (Item item : tracker.findAll()) {
            output.accept(String.format("Item item: name = %s, id = %s", item.getName(), item.getId()));
        }
        return true;
    }
}