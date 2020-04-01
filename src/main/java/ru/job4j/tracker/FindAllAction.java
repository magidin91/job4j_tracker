package ru.job4j.tracker;

import java.util.function.Consumer;

public class FindAllAction implements UserAction {
    @Override
    public String name() {
        return "=== Show all ====";
    }

    @Override
    public boolean execute(Input input, Consumer<String> output, ITracker tracker) {
        for (Item item : tracker.findAll()) {
            output.accept(String.format("id=%s, name=%s", item.getId(), item.getName()));
        }
        return true;
    }
}