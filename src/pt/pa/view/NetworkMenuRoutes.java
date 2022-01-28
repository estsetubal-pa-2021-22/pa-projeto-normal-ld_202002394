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

    public NetworkMenuRoutes() {
        this.setText("Routes");
        this.importRoutes = new MenuItem("Import");
        this.exportRoutes = new MenuItem("Export");
        this.getItems().addAll(importRoutes,exportRoutes);
    }

    public MenuItem getImportRoutesItem() {
        return this.importRoutes;
    }

    public MenuItem getExportRoutesItem() {
        return this.exportRoutes;
    }

}
