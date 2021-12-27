package pt.pa.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import pt.pa.model.NetworkManager;

public class NetworkMetrics extends HBox {

    NetworkManager manager;

    public NetworkMetrics(NetworkManager manager) {
        this.manager = manager;
        this.setSpacing(200);
        this.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: #FFFFFF;");
        this.setMinHeight(40);
        this.setAlignment(Pos.CENTER);
        update();
    }

    public void update() {
        // TO DO: apagar elementos existentes na HBox (esta classe é uma HBox)
        // criar novos elementos com a informação atualizada (utilizando o NetworkManager)
        // adicionar estes novos elementos à HBox
    }

}
