package ru.job4j.tracker;

import java.util.function.Consumer;

public class ReplaceAction implements UserAction {
    @Override
    public String name() {
        return "=== Edit item ====";
    }

    @Override
    public boolean execute(Input input, Consumer<String> output, Tracker tracker) {
        String id = input.askStr("Enter id: ");
        String name = input.askStr("Enter name: ");
        Item item = new Item(name);
        if (tracker.replace(id, item)) {
            output.accept("=== Done ====");
        } else {
            output.accept("No such item!");
        }
        return true;
    }
}
