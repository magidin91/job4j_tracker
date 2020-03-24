package ru.job4j.tracker;

import java.util.function.Consumer;

public class DeleteAction implements UserAction {
    @Override
    public String name() {
        return "=== Delete item ====";
    }

    @Override
    public boolean execute(Input input, Consumer<String> output, Tracker tracker) {
        String id = input.askStr("Enter id: ");
        if (tracker.delete(id)) {
            output.accept("=== Done ====");
        } else {
            output.accept("No such item!");
        }
        return true;
    }
}
