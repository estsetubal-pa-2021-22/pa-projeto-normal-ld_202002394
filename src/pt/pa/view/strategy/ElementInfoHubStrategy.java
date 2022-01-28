package pt.pa.view.strategy;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import pt.pa.model.Hub;
import pt.pa.model.NetworkManager;
/**
 * Class responsible for the Strategy Pattern, works as the "Concrete Strategy" entity for this pattern.
 * <br>
 * It shows the information of the hub.
 * <br>
 * It's "Pattern" entity is the {@link ElementInfoStrategy} interface.
 *
 * @author LD_202002394
 * @version Final
 */
public class ElementInfoHubStrategy implements ElementInfoStrategy {
    /**
     * Method part of the Strategy Pattern, overriding the update method when a hub is selected.
     *
     * @param manager NetworkManager
     * @param vbox    VBox
     * @param element Object
     */
    @Override
    public void update(NetworkManager manager, VBox vbox, Object element) {
        Hub hub = (Hub)element;

        vbox.getChildren().clear();
        if (manager.getVertex(hub) == null) {
            javafx.scene.control.Label puppetLabel = new Label("Select an element to see details.");
            vbox.getChildren().addAll(puppetLabel);
            return;
        }

        Circle circle = new Circle(15);
        circle.getStyleClass().add("vertex");

        Label hub_info = new Label(hub.toString());

        Label neighbors = new Label("Neighbors (" + manager.countNeighbors(hub) + ")");
        ListView neighbors_info = new ListView();
        neighbors_info.setMaxWidth(150);
        neighbors_info.setMaxHeight(120);
        neighbors_info.getItems().addAll(manager.getNeighbors(hub));

        Label population = new Label("Population");
        Label population_info = new Label (hub.getPopulation() + " people");

        Label location = new Label("Location");

        String x = String.valueOf((int)hub.getCoordinates().getX());
        String y = String.valueOf((int)hub.getCoordinates().getY());
        Label location_info = new Label("(" + x + ", " + y + ")");

        vbox.getChildren().addAll(circle, hub_info, neighbors, neighbors_info, population, population_info, location, location_info);

    }

}
