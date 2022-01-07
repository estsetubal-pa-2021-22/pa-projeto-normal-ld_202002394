package pt.pa.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import pt.pa.model.NetworkManager;
import pt.pa.model.Observer;

public class NetworkMetrics implements Observer {

    private HBox hbox;
    private  Label nHubs;
    private  Label isolatedHubs;
    private  Label nComponents;
    private  Label nRoutes;

    public NetworkMetrics(NetworkManager manager) {
        this.hbox = new HBox();
        isolatedHubs = new Label();
        nHubs = new Label();
        nComponents = new Label();
        nRoutes = new Label();

        hbox.setAlignment(Pos.CENTER);
        hbox.getStylesheets().add("metrics.css");
        hbox.getStyleClass().add("hbox");

        update(manager);
    }

    public HBox getHBox() {
        return hbox;
    }

    @Override
    public void update(Object obj) {
        NetworkManager manager = (NetworkManager)obj;
        hbox.getChildren().clear();

        nHubs.setText("Hubs: " + manager.countHubs());
        nRoutes.setText("Routes: " + manager.countRoutes());
        isolatedHubs.setText("Hubs Isolados: " + manager.countIsolatedHubs());
        nComponents.setText("Componentes: " + manager.countComponents());

        hbox.getChildren().addAll(nHubs, nComponents, isolatedHubs, nRoutes);
    }

}
