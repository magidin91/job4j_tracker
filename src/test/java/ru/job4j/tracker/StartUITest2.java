package ru.job4j.tracker;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.function.Consumer;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * C использованием функционального интерфейса и выводом в буффер.
 * Создаем реализацию функционального интерфейса в анонимном классе, реализуем вывод в буфер из new PrintStream(out).
 */
public class StartUITest2 {
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final Consumer<String> output = new Consumer<String>() {
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
        new StartUI(input, new Tracker(), output).init(new ArrayList<UserAction>(Arrays.asList(action)));
        assertThat(action.isCall(), is(true));
    }

    @Test
    public void whenPrtMenu() {
        StubInput input = new StubInput(
                new String[]{"0"}
        );
        StubAction action = new StubAction();
        new StartUI(input, new Tracker(), output).init(new ArrayList<UserAction>(Arrays.asList(action)));
        String expect = new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                .add("Menu.")
                .add("0. Stub action")
                .toString();
        assertThat(new String(out.toByteArray()), is(expect));
    }
}