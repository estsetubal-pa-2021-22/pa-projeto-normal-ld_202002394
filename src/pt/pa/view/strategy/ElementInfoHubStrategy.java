package pt.pa.view.strategy;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import pt.pa.model.Hub;
import pt.pa.model.NetworkManager;

import java.awt.*;

public class ElementInfoHubStrategy<E> implements ElementInfoStrategy<E> {

    @Override
    public void update(NetworkManager manager, VBox vbox, E element) {
        Hub hub = (Hub)element;
        vbox.getChildren().clear();
        if (manager.getVertex(hub) == null) {
            javafx.scene.control.Label puppetLabel = new Label("Nothing selected");
            vbox.getChildren().addAll(puppetLabel);
            return;
        }
        // TO DO: criar elementos javafx com informação do hub, e no final adicionar à "vbox" (recebido como parâmetro)

    }

}
