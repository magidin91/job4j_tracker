package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

public class StartUI {
    private final Input input;
    private final Tracker tracker;
    private final Consumer<String> output;

    public StartUI(Input input, Tracker tracker, Consumer<String> output) {
        this.input = input;
        this.tracker = tracker;
        this.output = output;
    }

    public void init(ArrayList<UserAction> actions) {
        boolean run = true;
        while (run) {
            this.showMenu(actions);
            int select = input.askInt("Select: ", actions.size());
            UserAction action = actions.get(select);
            run = action.execute(input, output, tracker);
        }
    }

    private void showMenu(ArrayList<UserAction> actions) {
        output.accept("Menu.");
        for (int index = 0; index < actions.size(); index++) {
            output.accept(index + ". " + actions.get(index).name());
        }
    }

    public static void main(String[] args) {
        Input input = new ConsoleInput();
        Input validate = new ValidateInput(input);
        Tracker tracker = new Tracker();
        ArrayList<UserAction> actions = new ArrayList<>(Arrays.asList(new CreateAction(), new FindAllAction(), new ReplaceAction(),
                new DeleteAction(), new FindItemByIdAction(), new FindItemsByName(), new ExitAction()
        ));
        new StartUI(validate, tracker, System.out::println).init(actions);
    }
}