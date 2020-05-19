package ru.job4j.tracker.actions;

import ru.job4j.tracker.tracker.ITracker;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.model.Item;

import java.util.function.Consumer;

public class CreateAction implements UserAction {
    @Override
    public String name() {
        return "=== Create Item ====";
    }

    @Override
    public boolean execute(Input input, Consumer<String> output, ITracker tracker) {
        output.accept("Enter name: ");
        String name = input.askStr("");
        Item item = new Item(name);
        tracker.add(item);
        output.accept("=== Done ====");
        output.accept(String.format("The item is created: name = %s, id = %s", item.getName(), item.getId()));
        return true;
    }
}