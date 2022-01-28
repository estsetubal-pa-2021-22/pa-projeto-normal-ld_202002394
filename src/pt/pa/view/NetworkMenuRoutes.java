package pt.pa.view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 * Class responsible for generating the option to import/export routes.
 *
 * @author LD_202002394
 * @version Final
 */

public class NetworkMenuRoutes extends Menu {

    private final MenuItem importRoutes;
    private final MenuItem exportRoutes;

    /**
     * Constructor of the class NetworkMenuRoutes.
     *
     */
    public NetworkMenuRoutes() {
        this.setText("Routes");
        this.importRoutes = new MenuItem("Import");
        this.exportRoutes = new MenuItem("Export");
        this.getItems().addAll(importRoutes,exportRoutes);
    }

    /**
     * Method to get the Import Routes Item.
     *
     * @return Returns the Import Routes Item.
     */
    public MenuItem getImportRoutesItem() {
        return this.importRoutes;
    }

    /**
     * Method to get the Export Routes Item.
     *
     * @return Returns the Export Routes Item.
     */
    public MenuItem getExportRoutesItem() {
        return this.exportRoutes;
    }

}
