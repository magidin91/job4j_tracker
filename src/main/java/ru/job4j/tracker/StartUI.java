package ru.job4j.tracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.tracker.actions.*;
import ru.job4j.tracker.input.ConsoleInput;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.input.ValidateInput;
import ru.job4j.tracker.tracker.ITracker;
import ru.job4j.tracker.tracker.Tracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

/**
 * The class launches the user interface
 */
public class StartUI {
    private static final Logger LOGGER = LoggerFactory.getLogger(StartUI.class);
    private final Input input;
    private final ITracker tracker;
    private final Consumer<String> output;

    public StartUI(Input input, ITracker tracker, Consumer<String> output) {
        this.input = input;
        this.tracker = tracker;
        this.output = output;
    }

    /**
     * Launches the app
     */
    public void init(ArrayList<UserAction> actions) {
        boolean run = true;
        while (run) {
            this.showMenu(actions);
            int select = input.askInt("Select: ", actions.size());
            UserAction action = actions.get(select);
            run = action.execute(input, output, tracker);
        }
    }

    /**
     * Shows the user menu
     */
    private void showMenu(ArrayList<UserAction> actions) {
        output.accept("Menu.");
        for (int index = 0; index < actions.size(); index++) {
            output.accept(index + ". " + actions.get(index).name());
        }
    }

    public static void main(String[] args) {
        ArrayList<UserAction> actions = new ArrayList<>(Arrays.asList(
                new CreateAction(), new EditAction(), new DeleteAction(),
                new FindItemByIdAction(), new FindItemsByNameAction(),
                new ShowAllAction(), new ExitAction()
        ));
        Input validate = new ValidateInput(new ConsoleInput());

       new StartUI(validate, new Tracker(), System.out::println).init(actions); //Launches Tracker with a list storage

//        try (TrackerSQL tracker = new TrackerSQL(new ConnectionCreator().init())) {
//            new StartUI(validate, tracker, System.out::println).init(actions); //Launches Tracker with database storage
//        } catch (Exception e) {
//            LOGGER.error(e.getMessage(), e);
//        }
    }
}