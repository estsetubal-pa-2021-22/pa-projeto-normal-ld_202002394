package pt.pa.view.strategy;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import pt.pa.model.NetworkManager;

public class ElementInfoNoneStrategy<E> implements ElementInfoStrategy<E> {

    @Override
    public void update(NetworkManager manager, VBox vbox, E element) {
        vbox.getChildren().clear();

        Label info = new Label("Select an element to see details.");

        vbox.getChildren().add(info);
    }

}
