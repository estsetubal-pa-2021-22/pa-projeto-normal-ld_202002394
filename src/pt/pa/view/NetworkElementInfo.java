package pt.pa.view;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import pt.pa.model.Hub;
import pt.pa.model.NetworkManager;
import pt.pa.model.Route;
import pt.pa.view.strategy.ElementInfoHubStrategy;
import pt.pa.view.strategy.ElementInfoNoneStrategy;
import pt.pa.view.strategy.ElementInfoRouteStrategy;
import pt.pa.view.strategy.ElementInfoStrategy;

public class NetworkElementInfo<E> extends VBox {

    // Strategy for the element details' creation (none, hub or route)
    ElementInfoStrategy elementInfoStrategy;
    NetworkManager manager;

    // Constructor for when there's not an element selected
    public NetworkElementInfo() {
        setElementInfoStrategy(new ElementInfoNoneStrategy());
        create(null);
    }

    // Constructor for when there's a hub selected
    public NetworkElementInfo(NetworkManager manager, Hub hub) {
        this.manager = manager;
        setElementInfoStrategy(new ElementInfoHubStrategy());
        create((E)hub);
    }

    // Constructor for when there's a route selected
    public NetworkElementInfo(NetworkManager manager, Route route) {
        this.manager = manager;
        setElementInfoStrategy(new ElementInfoRouteStrategy());
        create((E)route);
    }

    // Set actual strategy for the details' creation
    public void setElementInfoStrategy(ElementInfoStrategy elementInfoStrategy) {
        this.elementInfoStrategy = elementInfoStrategy;
    }

    public ElementInfoStrategy getElementInfoStrategy() {
        return elementInfoStrategy;
    }

    // Style the VBox and create elements inside VBox
    private void create(E element) {
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: #FFFFFF;");
        this.setMinWidth(300);
        elementInfoStrategy.update(manager,this,element);
    }

}
