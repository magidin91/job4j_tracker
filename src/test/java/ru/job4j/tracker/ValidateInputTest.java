package ru.job4j.tracker;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ValidateInputTest {

    @Test
    public void whenInvalidInput() {
        ByteArrayOutputStream mem = new ByteArrayOutputStream();
        PrintStream out = System.out;
        System.setOut(new PrintStream(mem));
        ValidateInput validateInput = new ValidateInput(
                new StubInput(new String[]{"invalid", "1"})
        );
        validateInput.askInt("Enter", 6);
        assertThat(
                mem.toString(),
                is(String.format("Please enter validate data again %n"))
        );
        System.setOut(out);
    }

    @Test
    public void whenIllegalState() {
        ByteArrayOutputStream mem = new ByteArrayOutputStream();
        PrintStream out = System.out;
        System.setOut(new PrintStream(mem));
        ValidateInput validateInput = new ValidateInput(
                new StubInput(new String[]{"7", "1"})
        );
        validateInput.askInt("Enter", 6);
        assertThat(
                mem.toString(),
                is(String.format("Please select key from menu %n"))
        );
        System.setOut(out);
    }
}