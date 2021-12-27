package pt.pa.view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import pt.pa.model.Action;

import java.util.Stack;

public class NetworkMenuUndo extends Menu {

    private Stack<Action> actions;

    private MenuItem undoAction;

    public NetworkMenuUndo() {
        actions = new Stack<>();
        this.setText("Undo");
        this.undoAction = new MenuItem("Undo last action");
        this.getItems().addAll(undoAction);
    }

    public MenuItem getUndoActionItem() {
        return this.undoAction;
    }

    public void saveAction(Action action) {
        actions.add(action);
    }

    public Action undoAction() {
        if (actions.isEmpty())
            return null;
        return actions.pop();
    }

}
