package pt.pa.model;

import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pt.pa.view.NetworkUI;

/**
 * Class responsible for starting the application.
 * <br>
 * Includes an instance of {@link NetworkManager}.
 *
 * @author LD_202002394
 * @version Final
 */
public class NetworkController {

    private final NetworkManager manager;
    private final NetworkUI networkUI;

    private final Stage stage;
    private Scene scene;
    private SmartGraphPanel<Hub, Route> graphView;

    /**
     * Constructor of the class NetworkController.
     *
     * @param folder        String
     * @param routesFile    String
     *
     */
    public NetworkController(String folder, String routesFile) {
        manager = new NetworkManager(folder, routesFile);
        stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Projeto PA - Logistics Network");
        stage.setMinWidth(1400);
        stage.setMinHeight(800);
        stage.setResizable(false);
        graphView = new SmartGraphPanel<>(manager.getGraph());
        networkUI = new NetworkUI(this);
        graphView.setId("pane");
    }

    /**
     * Method to initialize the scene.
     *
     */
    public void start() {
        scene = new Scene(networkUI, 1400, 800);
        stage.setScene(scene);
        networkUI.getEventHandler().createElementsInfoEvent();
        stage.show();
        graphView.init();
        setCoordinates();
    }

    /**
     * Setter method for the graphView variable.
     *
     * @param graphView     SmartGraphPanel<Hub, Route>
     */
    public void setGraphView(SmartGraphPanel<Hub, Route> graphView) {
        this.graphView = graphView;
    }

    /**
     * Getter method for the manager variable.
     *
     */
    public NetworkManager getManager() {
        return this.manager;
    }

    /**
     * Getter method for the graphView variable.
     *
     */
    public SmartGraphPanel<Hub, Route> getGraphView() {
        return this.graphView;
    }

    /**
     * Getter method for the stage variable.
     *
     */
    public Stage getStage() {
        return this.stage;
    }

    /**
     * Getter method for the scene variable.
     *
     */
    public Scene getScene() {
        return this.scene;
    }

    /**
     * Setter method for the coordinates variable of the {@link NetworkManager} class.
     *
     */
    public void setCoordinates() {
        manager.setCoordinates(graphView);
    }

}
