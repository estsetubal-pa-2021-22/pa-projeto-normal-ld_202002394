package pt.pa.view.strategy;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import pt.pa.model.Hub;
import pt.pa.model.NetworkManager;

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

        Circle circle = new Circle(15);
        circle.getStyleClass().add("vertex");

        Label hub_info = new Label(hub.toString());

        Label neighbors = new Label("Neighbors");
        ListView neighbors_info = new ListView();
        neighbors_info.setMaxWidth(150);
        neighbors_info.setMaxHeight(120);
        neighbors_info.getItems().addAll(manager.getNeighbors(hub));

        Label population = new Label("Population");
        Label population_info = new Label (String.valueOf(hub.getPopulation()));

        Label location = new Label("Location");

        String x = String.valueOf((int)hub.getCoordinates().getX());
        String y = String.valueOf((int)hub.getCoordinates().getY());
        Label location_info = new Label("(" + x + "," + y + ")");

        vbox.getChildren().addAll(circle, hub_info, neighbors, neighbors_info, population, population_info, location, location_info);

    }

}
