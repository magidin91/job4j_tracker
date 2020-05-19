package ru.job4j.tracker.actions;

import ru.job4j.tracker.tracker.ITracker;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.model.Item;

import java.util.function.Consumer;

public class FindItemByIdAction implements UserAction {
    @Override
    public String name() {
        return "=== Find item by Id ====";
    }

    @Override
    public boolean execute(Input input, Consumer<String> output, ITracker tracker) {
        String id = input.askStr("Enter id: ");
        Item item = tracker.findById(id);
        if (item != null) {
            output.accept("=== Done ====");
            output.accept(String.format("Item item: name = %s, id = %s", item.getName(), item.getId()));
        } else {
            output.accept("No such item!");
        }
        return true;
    }
}
