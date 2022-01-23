package pt.pa.view.strategy;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pt.pa.model.NetworkManager;

public class ElementInfoNoneStrategy implements ElementInfoStrategy {

    @Override
    public void update(NetworkManager manager, VBox vbox, Object element) {
        vbox.getChildren().clear();

        Label info = new Label("Select an element to see details.");

        vbox.getChildren().add(info);
    }

}
