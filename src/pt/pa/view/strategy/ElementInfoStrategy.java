package pt.pa.view.strategy;

import javafx.scene.layout.VBox;
import pt.pa.model.NetworkManager;

public interface ElementInfoStrategy {

    void update(NetworkManager manager, VBox vbox, Object element);

}
