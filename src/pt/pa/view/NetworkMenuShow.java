package pt.pa.view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class NetworkMenuShow extends Menu {

    private MenuItem farthestHubs;
    private MenuItem hubsWithMostNeighbors;

    public NetworkMenuShow() {
        this.setText("Show");
        this.farthestHubs = new MenuItem("Farthest Hubs");
        this.hubsWithMostNeighbors = new MenuItem("Hubs with most neighbors");
        this.getItems().addAll(farthestHubs,hubsWithMostNeighbors);
    }

    public MenuItem getFarthestHubsItem() {
        return this.farthestHubs;
    }

    public MenuItem getHubsWithMostNeighborsItem() {
        return this.hubsWithMostNeighbors;
    }

}
