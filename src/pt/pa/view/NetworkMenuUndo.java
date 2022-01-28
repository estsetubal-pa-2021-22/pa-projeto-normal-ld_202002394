package pt.pa.view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import pt.pa.model.actions.Action;

import java.util.Stack;

/**
 * Class responsible for generating the misc options of the user, like the "Undo" and "Default Styling" features.
 *
 * @author LD_202002394
 * @version Final
 */

public class NetworkMenuUndo extends Menu {

    private final Stack<Action> actions;

    private final MenuItem undoAction;
    private final MenuItem defaultStyling;

    /**
     * Constructor of the class NetworkMenuUndo.
     *
     */
    public NetworkMenuUndo() {
        actions = new Stack<>();
        this.setText("More");
        this.undoAction = new MenuItem("Undo last action");
        this.defaultStyling = new MenuItem("Default Styling");
        this.getItems().addAll(defaultStyling,undoAction);
    }

    /**
     * Method to get the Undo Action Item.
     *
     * @return Returns the Undo Action Item.
     */
    public MenuItem getUndoActionItem() {
        return this.undoAction;
    }

    /**
     * Method to get the Default Styling Item.
     *
     * @return Returns the Default Styling Item.
     */
    public MenuItem getDefaultStylingItem() { return this.defaultStyling; }

    /**
     * Method to save action.
     *
     * @param action Action
     *
     */
    public void saveAction(Action action) {
        actions.add(action);
    }

    /**
     * Method to show the undo action.
     *
     * @return Returns the undo action.
     */
    public Action undoAction() {
        if (actions.isEmpty())
            return null;
        return actions.pop();
    }

}
