package pt.pa.view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import pt.pa.model.Action;

import java.util.Stack;

public class NetworkMenuUndo extends Menu {

    private Stack<Action> actions;

    private MenuItem undoAction;
    private MenuItem defaultStyling;

    public NetworkMenuUndo() {
        actions = new Stack<>();
        this.setText("More");
        this.undoAction = new MenuItem("Undo last action");
        this.defaultStyling = new MenuItem("Default Styling");
        this.getItems().addAll(defaultStyling,undoAction);
    }

    public MenuItem getUndoActionItem() {
        return this.undoAction;
    }

    public MenuItem getDefaultStylingItem() { return this.defaultStyling; }

    public void saveAction(Action action) {
        actions.add(action);
    }

    public Action undoAction() {
        if (actions.isEmpty())
            return null;
        return actions.pop();
    }

}
