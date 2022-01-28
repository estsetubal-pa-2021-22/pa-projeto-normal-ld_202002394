package pt.pa.view.strategy;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pt.pa.model.NetworkManager;
/**
 * Class responsible for the Strategy Pattern, works as the "Concrete Strategy" entity for this pattern.
 * <br>
 * It shows no information, when the user doesn't select a route or a hub.
 * <br>
 * It's "Pattern" entity is the {@link ElementInfoStrategy} interface.
 *
 * @author LD_202002394
 * @version Final
 */
public class ElementInfoNoneStrategy implements ElementInfoStrategy {
    /**
     * Method part of the Strategy Pattern, overriding the update method when nothing is selected.
     *
     * @param manager NetworkManager
     * @param vbox    VBox
     * @param element Object
     */
    @Override
    public void update(NetworkManager manager, VBox vbox, Object element) {
        vbox.getChildren().clear();

        Label info = new Label("Select an element to see details.");

        vbox.getChildren().add(info);
    }

}
