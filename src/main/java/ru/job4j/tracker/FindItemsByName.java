package ru.job4j.tracker;

import java.util.function.Consumer;

public class FindItemsByName implements UserAction {
    @Override
    public String name() {
        return "=== Find items by name ====";
    }

    @Override
    public boolean execute(Input input, Consumer<String> output, Tracker tracker) {
        String name = input.askStr("Enter name: ");
        for (Item item : tracker.findByName(name)) { //выводим элементы
            if (item != null) {
                output.accept(item.toString());
            } else {
                output.accept("No such item!");
            }
        }
        return true;
    }
}
