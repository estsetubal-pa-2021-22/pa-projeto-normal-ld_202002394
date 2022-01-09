package pt.pa.view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class NetworkMenuRemove extends Menu {

    private MenuItem removeHub;
    private MenuItem removeRoute;

    public NetworkMenuRemove() {
        this.setText("Remove");
        this.removeHub = new MenuItem("Hub");
        this.removeRoute = new MenuItem("Route");
        this.getItems().addAll(removeHub,removeRoute);
    }

    public MenuItem getRemoveHubItem() {
        return this.removeHub;
    }

    public MenuItem getRemoveRouteItem() {
        return this.removeRoute;
    }

}
