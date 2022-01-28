package pt.pa.view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 * Class responsible for generating and assuring the functionality of the items linked to creating routes and hubs.
 *
 * @author LD_202002394
 * @version Final
 */

public class NetworkMenuCreate extends Menu {

    private final MenuItem createHub;
    private final MenuItem createRoute;

    /**
     * Constructor of the class NetworkMenuCreate.
     *
     */
    public NetworkMenuCreate() {
        this.setText("Create");
        this.createHub = new MenuItem("Hub");
        this.createRoute = new MenuItem("Route");
        this.getItems().addAll(createHub,createRoute);
    }

    /**
     * Method to get the Create Hub Item.
     *
     * @return Returns the Create Hub Item.
     */
    public MenuItem getCreateHubItem() {
        return this.createHub;
    }

    /**
     * Method to get the Create Route Item.
     *
     * @return Returns the Create Route Item.
     */
    public MenuItem getCreateRouteItem() {
        return this.createRoute;
    }

}
