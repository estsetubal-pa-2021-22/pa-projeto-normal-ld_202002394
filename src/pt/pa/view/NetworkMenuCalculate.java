package pt.pa.view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class NetworkMenuCalculate extends Menu {

    private MenuItem shortestPath;
    private MenuItem distancePath;

    public NetworkMenuCalculate() {
        this.setText("Calculate");
        this.shortestPath = new MenuItem("Shortest Path");
        this.distancePath = new MenuItem("Distance between 2 paths");
        this.getItems().addAll(shortestPath);
    }

    public MenuItem getShortestPathItem() {
        return this.shortestPath;
    }

    public MenuItem getDistancePathItem() {
        return this.distancePath;
    }

}
