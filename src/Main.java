import javafx.application.Application;
import javafx.stage.Stage;
import pt.pa.model.*;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        NetworkController controller = new NetworkController("dataset/sgb32", "routes_1.txt");
        controller.start();
    }

}