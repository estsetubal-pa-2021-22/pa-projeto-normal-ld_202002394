package pt.pa.view.strategy;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pt.pa.model.NetworkManager;

public class ElementInfoNoneStrategy<E> implements ElementInfoStrategy<E> {

    @Override
    public void update(NetworkManager manager, VBox vbox, E element) {
        // TO DO: criar elementos javafx com informação de nenhum elemento selecionado, e no final adicionar à "vbox" (recebido como parâmetro)

    }

}
