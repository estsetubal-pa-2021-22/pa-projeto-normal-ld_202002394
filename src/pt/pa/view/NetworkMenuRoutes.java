package pt.pa.view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

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
