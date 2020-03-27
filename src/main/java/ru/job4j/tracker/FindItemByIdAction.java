package ru.job4j.tracker;

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
            output.accept(item.toString());
        } else {
            output.accept("No such item!");
        }
        return true;
    }
}
