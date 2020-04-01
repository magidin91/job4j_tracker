package ru.job4j.tracker;

import java.util.function.Consumer;

public class StubAction implements UserAction {
    private boolean call = false;

    @Override
    public String name() {
        return "Stub action";
    }

    @Override
    public boolean execute(Input input, Consumer<String> output, ITracker tracker) {
        call = true;
        return false;
    }

    public boolean isCall() {
        return call;
    }
}