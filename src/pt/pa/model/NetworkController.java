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

    public void start() {
        scene = new Scene(networkUI, 1400, 800);
        stage.setScene(scene);
        networkUI.getEventHandler().createElementsInfoEvent();
        stage.show();
        graphView.init();
        setCoordinates();
    }

    public void setGraphView(SmartGraphPanel<Hub, Route> graphView) {
        this.graphView = graphView;
    }

    public NetworkManager getManager() {
        return this.manager;
    }

    public SmartGraphPanel<Hub, Route> getGraphView() {
        return this.graphView;
    }

    public Stage getStage() {
        return this.stage;
    }

    public Scene getScene() {
        return this.scene;
    }

    public void setCoordinates() {
        manager.setCoordinates(graphView);
    }

}
