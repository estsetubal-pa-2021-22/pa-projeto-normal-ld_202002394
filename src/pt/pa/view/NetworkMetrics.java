package pt.pa.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import pt.pa.model.NetworkManager;
import pt.pa.model.Observer;

/**
 * Class responsible for generating and assuring the functionality of the items found
 * on the bottom of the application, containing the metrics of the program, like the number of "Hubs" or "Routes".
 *
 * @author LD_202002394
 * @version Final
 */

public class NetworkMetrics implements Observer {

    private final HBox  hbox;
    private final Label nHubs;
    private final Label isolatedHubs;
    private final Label nComponents;
    private final Label nRoutes;

    /**
     * Constructor of the class NetworkMetrics.
     *
     * @param manager NetworkManager
     */
    public NetworkMetrics(NetworkManager manager) {
        this.hbox = new HBox();
        isolatedHubs = new Label();
        nHubs = new Label();
        nComponents = new Label();
        nRoutes = new Label();

        hbox.setAlignment(Pos.CENTER);
        hbox.getStylesheets().add("metrics.css");
        hbox.getStyleClass().add("hbox");

        update(manager);
    }

    /**
     * Method to get hbox.
     *
     * @return Returns the hbox.
     */
    public HBox getHBox() {
        return hbox;
    }

    /**
     * Method to update the hbox.
     *
     */
    @Override
    public void update(Object obj) {
        NetworkManager manager = (NetworkManager)obj;
        hbox.getChildren().clear();

        nHubs.setText("Hubs: " + manager.countHubs());
        nRoutes.setText("Routes: " + manager.countRoutes());
        isolatedHubs.setText("Isolated Hubs: " + manager.countIsolatedHubs());
        nComponents.setText("Components: " + manager.countComponents());

        hbox.getChildren().addAll(nHubs, nRoutes, isolatedHubs, nComponents);
    }

}
