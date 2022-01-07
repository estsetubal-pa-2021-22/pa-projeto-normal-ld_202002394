package pt.pa.model;

import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pt.pa.view.NetworkUI;

public class NetworkController {

    private NetworkManager manager;
    private NetworkUI networkUI;

    private Stage stage;
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

    public Stage start() {
        scene = new Scene(networkUI, 1400, 800);
        stage.setScene(scene);
        networkUI.getEventHandler().createElementsInfoEvent();
        return stage;
    }

    public void init() {
        graphView.init();
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
