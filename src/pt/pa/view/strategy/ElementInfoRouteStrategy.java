package pt.pa.view.strategy;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import pt.pa.model.Hub;
import pt.pa.model.NetworkManager;
import pt.pa.model.Route;

public class ElementInfoRouteStrategy implements ElementInfoStrategy {

    @Override
    public void update(NetworkManager manager, VBox vbox, Object element) {
        Route route = (Route)element;
        vbox.getChildren().clear();
        if (manager.getEdge(route) == null) {
            javafx.scene.control.Label puppetLabel = new Label("Select an element to see details.");
            vbox.getChildren().addAll(puppetLabel);
            return;
        }

        Label route_info = new Label(route.toString());

        Circle circle_origin = new Circle(15);
        circle_origin.getStyleClass().add("vertex");

        Circle circle_destination = new Circle(15);
        circle_destination.getStyleClass().add("vertex");

        Line line_origin = new Line(1,1,1,50);
        line_origin.getStyleClass().add("edge");
        Line line_destination = new Line(1,1,1,50);
        line_destination.getStyleClass().add("edge");

        Hub hub_origin = manager.getEdge(route).vertices()[0].element();
        Hub hub_destination = manager.getEdge(route).vertices()[1].element();

        Label route_hubs_origin = new Label(hub_origin.toString());
        Label route_hubs_destination = new Label(hub_destination.toString());

        vbox.getChildren().addAll(route_hubs_origin,circle_origin, line_origin,route_info, line_destination, circle_destination,route_hubs_destination);

    }

}
