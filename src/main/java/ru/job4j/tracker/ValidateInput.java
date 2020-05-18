package ru.job4j.tracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidateInput implements Input {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateInput.class);
    private final Input input;

    public ValidateInput(Input input) {
        this.input = input;
    }

    @Override
    public String askStr(String question) {
        return input.askStr(question);
    }

    @Override
    public int askInt(String question) {
        return Integer.valueOf(askStr(question));
    }

    @Override
    public int askInt(String question, int max) {
        boolean invalid = true;
        int value = -1;
        do {
            try {
                value = input.askInt(question, max);
                invalid = false;
            } catch (IllegalStateException moe) {
                LOGGER.info("Please select key from menu ");
            } catch (NumberFormatException nfe) {
                LOGGER.info("Please enter validate data again ");
            }
        } while (invalid);
        return value;
    }
}