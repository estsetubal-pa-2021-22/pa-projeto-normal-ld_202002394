package pt.pa.view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class NetworkMenuCreate extends Menu {

    private MenuItem createHub;
    private MenuItem createRoute;

    public NetworkMenuCreate() {
        this.setText("Create");
        this.createHub = new MenuItem("Hub");
        this.createRoute = new MenuItem("Route");
        this.getItems().addAll(createHub,createRoute);
    }

    public MenuItem getCreateHubItem() {
        return this.createHub;
    }

    public MenuItem getCreateRouteItem() {
        return this.createRoute;
    }

}
