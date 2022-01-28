package pt.pa.view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 * Class responsible for generating the option to remove routes and hubs.
 *
 * @author LD_202002394
 * @version Final
 */

public class NetworkMenuRemove extends Menu {

    private final MenuItem removeHub;
    private final MenuItem removeRoute;

    /**
     * Constructor of the class NetworkMenuRemove.
     *
     */
    public NetworkMenuRemove() {
        this.setText("Remove");
        this.removeHub = new MenuItem("Hub");
        this.removeRoute = new MenuItem("Route");
        this.getItems().addAll(removeHub,removeRoute);
    }

    /**
     * Method to get the Remove Hub Item.
     *
     * @return Returns the Remove Hub Item.
     */
    public MenuItem getRemoveHubItem() {
        return this.removeHub;
    }

    /**
     * Method to get the Remove Route Item.
     *
     * @return Returns the Remove Route Item.
     */
    public MenuItem getRemoveRouteItem() {
        return this.removeRoute;
    }

}
