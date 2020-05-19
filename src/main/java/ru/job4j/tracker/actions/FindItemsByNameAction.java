package ru.job4j.tracker.actions;

import ru.job4j.tracker.tracker.ITracker;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.model.Item;

import java.util.function.Consumer;

public class FindItemsByNameAction implements UserAction {
    @Override
    public String name() {
        return "=== Find items by name ====";
    }

    @Override
    public boolean execute(Input input, Consumer<String> output, ITracker tracker) {
        String name = input.askStr("Enter name: ");
        output.accept("=== Done ====");
        for (Item item : tracker.findByName(name)) {
            if (item != null) {
                output.accept(String.format("Item item: name = %s, id = %s", item.getName(), item.getId()));
            } else {
                output.accept("No such item!");
            }
        }
        return true;
    }
}
