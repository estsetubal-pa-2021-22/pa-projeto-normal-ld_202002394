package pt.pa.view.strategy;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pt.pa.model.NetworkManager;
import pt.pa.model.Route;

public class ElementInfoRouteStrategy<E> implements ElementInfoStrategy<E> {

    @Override
    public void update(NetworkManager manager, VBox vbox, E element) {
        Route route = (Route)element;
        vbox.getChildren().clear();
        if (manager.getEdge(route) == null) {
            javafx.scene.control.Label puppetLabel = new Label("Nothing selected");
            vbox.getChildren().addAll(puppetLabel);
            return;
        }
        // TO DO: criar elementos javafx com informação da route, e no final adicionar à "vbox" (recebido como parâmetro)

    }

}
