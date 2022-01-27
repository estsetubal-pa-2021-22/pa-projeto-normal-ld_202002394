package pt.pa.view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 * Class responsible for generating and assuring the functionality of the items found
 * on the top of the application under the "Calculate" LOV.
 *
 * @author LD_202002394
 * @version Final
 */

public class NetworkMenuCalculate extends Menu {

    private final MenuItem shortestPath;
    private final MenuItem farthestHub;
    private final MenuItem farthestHubs;
    private final MenuItem closeHubs;

    public NetworkMenuCalculate() {
        this.setText("Calculate");
        this.shortestPath = new MenuItem("Shortest Path");
        this.farthestHub = new MenuItem("Farthest Hub");
        this.farthestHubs = new MenuItem("Farthest Hubs");
        this.closeHubs = new MenuItem("Close Hubs");
        this.getItems().addAll(shortestPath,farthestHub,farthestHubs,closeHubs);
    }

    public MenuItem getShortestPathItem() {
        return this.shortestPath;
    }

    public MenuItem getFarthestHubItem() {
        return this.farthestHub;
    }

    public MenuItem getFarthestHubsItem() {
        return this.farthestHubs;
    }

    public MenuItem getCloseHubsItem() { return this.closeHubs; }

}
