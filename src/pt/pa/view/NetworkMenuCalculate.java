package pt.pa.view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class NetworkMenuCalculate extends Menu {

    private MenuItem shortestPath;
    private MenuItem farthestHub;
    private MenuItem closeHubs;
    private MenuItem distancePath;

    public NetworkMenuCalculate() {
        this.setText("Calculate");
        this.shortestPath = new MenuItem("Shortest Path");
        this.farthestHub = new MenuItem("Farthest Hub");
        this.closeHubs = new MenuItem("Close Hubs");
        this.distancePath = new MenuItem("Distance between 2 paths");
        this.getItems().addAll(shortestPath,farthestHub,closeHubs);
    }

    public MenuItem getShortestPathItem() {
        return this.shortestPath;
    }

    public MenuItem getFarthestHubItem() {
        return this.farthestHub;
    }

    public MenuItem getCloseHubsItem() { return this.closeHubs; }

    public MenuItem getDistancePathItem() {
        return this.distancePath;
    }

}
