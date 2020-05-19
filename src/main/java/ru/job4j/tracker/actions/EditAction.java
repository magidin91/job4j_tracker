package ru.job4j.tracker.actions;

import ru.job4j.tracker.tracker.ITracker;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.model.Item;

import java.util.function.Consumer;

public class EditAction implements UserAction {
    @Override
    public String name() {
        return "=== Edit item ====";
    }

    @Override
    public boolean execute(Input input, Consumer<String> output, ITracker tracker) {
        String id = input.askStr("Enter id: ");
        String name = input.askStr("Enter name: ");
        Item item = new Item(name);
        if (tracker.replace(id, item)) {
            output.accept("=== Done ====");
            output.accept("=== The item has been edited ====");
        } else {
            output.accept("No item with such id!");
        }
        return true;
    }
}
