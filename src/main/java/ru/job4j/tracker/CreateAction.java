package ru.job4j.tracker;

import java.util.function.Consumer;

public class CreateAction implements UserAction {
    @Override
    public String name() {
        return "=== Create a new Item ====";
    }

    @Override
    public boolean execute(Input input, Consumer<String> output, ITracker tracker) {
        output.accept("Enter name: ");
        String name = input.askStr("");
        Item item = new Item(name);
        tracker.add(item);
        output.accept("=== Done ====");
        output.accept("Created item: name = " + item.getName() + ", id = " + item.getId());
        return true;
    }
}