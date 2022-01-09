package pt.pa.view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class NetworkMenuCalculate extends Menu {

    private MenuItem shortestPath;
    private MenuItem farthestHub;
    private MenuItem farthestHubs;
    private MenuItem closeHubs;
    private MenuItem distancePath;

    public NetworkMenuCalculate() {
        this.setText("Calculate");
        this.shortestPath = new MenuItem("Shortest Path");
        this.farthestHub = new MenuItem("Farthest Hub");
        this.farthestHubs = new MenuItem("Farthest Hubs");
        this.closeHubs = new MenuItem("Close Hubs");
        this.distancePath = new MenuItem("Distance between 2 paths");
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

    public MenuItem getDistancePathItem() {
        return this.distancePath;
    }

}
