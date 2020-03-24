package ru.job4j.tracker;

import org.junit.Test;

public class StubInputTest {
    @Test(expected = NumberFormatException.class)
            public void whenNotNumber() {
            new StubInput(new String[]{"text"}).askInt("Select: ", 6);
            }
    @Test(expected = IllegalStateException.class)
    public void whenOutOfBounds() {
        new StubInput(new String[]{"7"}).askInt("Select ", 6);
    }
}