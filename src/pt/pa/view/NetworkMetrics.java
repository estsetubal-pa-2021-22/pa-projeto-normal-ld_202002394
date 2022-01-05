package pt.pa.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import pt.pa.model.NetworkManager;
import pt.pa.model.Observer;

public class NetworkMetrics implements Observer {

    private HBox hbox;

    public NetworkMetrics(NetworkManager manager) {
        this.hbox = new HBox();
        hbox.setSpacing(200);
        hbox.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: #FFFFFF;");
        hbox.setMinHeight(40);
        hbox.setAlignment(Pos.CENTER);
        update(manager);
    }

    public HBox getHBox() {
        return hbox;
    }

    @Override
    public void update(Object obj) {
        NetworkManager manager = (NetworkManager)obj;
        hbox.getChildren().clear();
        // Criar elementos javafx com informação das 4 métricas e adicionar ao atributo hbox
        // Métricas: No. Hubs, No. Routes, Isolated Hubs, No. Components

    }

}
