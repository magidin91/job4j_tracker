package ru.job4j.tracker;

import org.junit.Test;
import ru.job4j.tracker.actions.StubAction;
import ru.job4j.tracker.input.StubInput;
import ru.job4j.tracker.tracker.Tracker;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringJoiner;
import java.util.function.Consumer;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Redirecting output to an array of bytes using an anonymous class
 */
public class StartUITest {
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    private final Consumer<String> output = new Consumer<>() {
        private final PrintStream stdout = new PrintStream(out);
        @Override
        public void accept(String s) {
            stdout.println(s);
        }
    };


    @Test
    public void whenExit() {
        StubInput input = new StubInput(
                new String[]{"0"}
        );
        StubAction action = new StubAction();
        new StartUI(input, new Tracker(), output).init(new ArrayList<>(Collections.singletonList(action)));
        assertThat(action.isCall(), is(true));
    }

    @Test
    public void whenPrtMenu() {
        StubInput input = new StubInput(
                new String[]{"0"}
        );
        StubAction action = new StubAction();
        new StartUI(input, new Tracker(), output).init(new ArrayList<>(Collections.singletonList(action)));
        String expect = new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                .add("Menu.")
                .add("0. Stub action")
                .toString();
        assertThat(new String(out.toByteArray()), is(expect));
    }
}