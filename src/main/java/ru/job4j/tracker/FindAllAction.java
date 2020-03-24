package ru.job4j.tracker;

import java.util.function.Consumer;

public class FindAllAction implements UserAction {
    @Override
    public String name() {
        return "=== Show all ====";
    }

    @Override
    public boolean execute(Input input, Consumer<String> output, Tracker tracker) {
        for (Item item : tracker.findAll()) {
            output.accept(String.format("%s %s", item.getId(), item.getName()));
        }
        return true;
    }
}