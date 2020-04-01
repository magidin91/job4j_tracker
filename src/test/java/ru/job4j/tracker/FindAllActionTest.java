package ru.job4j.tracker;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.StringJoiner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FindAllActionTest {

    @Test
    public void whenCheckOutput() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream std = System.out;
        System.setOut(new PrintStream(out));
        Tracker tracker = new Tracker();
        Item item = new Item("fix bug");
        tracker.add(item);
        FindAllAction act = new FindAllAction();
        act.execute(new StubInput(new String[]{}), System.out::println, tracker);
        String expect = new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                .add("id=" + item.getId() + ", name=" + item.getName())
                .toString();
        assertThat(new String(out.toByteArray()), is(expect));
        System.setOut(std);
    }
}