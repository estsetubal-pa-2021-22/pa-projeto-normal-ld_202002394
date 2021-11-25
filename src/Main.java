import com.brunomnsilva.smartgraph.containers.SmartGraphDemoContainer;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pt.pa.model.Hub;
import pt.pa.model.NetworkManager;
import pt.pa.model.Route;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        NetworkManager manager = new NetworkManager("dataset/sgb32", "routes_1.txt");

        SmartGraphPanel<Hub, Route> graphView = new SmartGraphPanel<>(manager.getGraph());
        Scene scene = new Scene(new SmartGraphDemoContainer(graphView), 1200, 768);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Projeto PA - Logistics Network");
        stage.setMinHeight(768);
        stage.setMinWidth(1200);
        stage.setScene(scene);
        stage.show();

        manager.setCoordinates(graphView);

    }

}
