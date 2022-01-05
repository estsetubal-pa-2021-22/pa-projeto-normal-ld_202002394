package pt.pa.view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class NetworkMenuShow extends Menu {

    private MenuItem farthestHubs;
    private MenuItem centrality;
    private MenuItem hubsWithMostNeighbors;

    public NetworkMenuShow() {
        this.setText("Show");
        this.farthestHubs = new MenuItem("Farthest Hubs");
        this.centrality = new MenuItem("Hub Centrality");
        this.hubsWithMostNeighbors = new MenuItem("Hubs with most neighbors");
        this.getItems().addAll(farthestHubs,centrality,hubsWithMostNeighbors);
    }

    public MenuItem getFarthestHubsItem() {
        return this.farthestHubs;
    }

    public MenuItem getCentralityItem() {
        return this.centrality;
    }

    public MenuItem getHubsWithMostNeighborsItem() {
        return this.hubsWithMostNeighbors;
    }

}
