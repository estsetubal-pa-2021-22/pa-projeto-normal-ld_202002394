package pt.pa.view.strategy;

import javafx.scene.layout.VBox;
import pt.pa.model.Hub;
import pt.pa.model.NetworkManager;

public class ElementInfoHubStrategy<E> implements ElementInfoStrategy<E> {

    @Override
    public void update(NetworkManager manager, VBox vbox, E element) {
        Hub hub = (Hub)element;
        // TO DO: criar elementos javafx com informação do hub, e no final adicionar à "vbox" (recebido como parâmetro)

    }

}
