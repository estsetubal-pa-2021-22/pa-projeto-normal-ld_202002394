package pt.pa.view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 * Class responsible for generating the option to select the "HUB Centrality" button
 * and the "Hubs with most neighbors" button.
 *
 * @author LD_202002394
 * @version Final
 */

public class NetworkMenuShow extends Menu {

    private final MenuItem centrality;
    private final MenuItem hubsWithMostNeighbors;

    public NetworkMenuShow() {
        this.setText("Show");
        this.centrality = new MenuItem("Hub Centrality");
        this.hubsWithMostNeighbors = new MenuItem("Hubs with most neighbors");
        this.getItems().addAll(centrality,hubsWithMostNeighbors);
    }

    public MenuItem getCentralityItem() {
        return this.centrality;
    }

    public MenuItem getHubsWithMostNeighborsItem() {
        return this.hubsWithMostNeighbors;
    }

}
