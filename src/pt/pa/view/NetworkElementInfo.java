package pt.pa.view;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import pt.pa.model.Hub;
import pt.pa.model.NetworkManager;
import pt.pa.model.Observer;
import pt.pa.model.Route;
import pt.pa.view.strategy.ElementInfoHubStrategy;
import pt.pa.view.strategy.ElementInfoNoneStrategy;
import pt.pa.view.strategy.ElementInfoRouteStrategy;
import pt.pa.view.strategy.ElementInfoStrategy;

public class NetworkElementInfo<E> implements Observer {

    // Strategy for the element details' creation (none, hub or route)
    ElementInfoStrategy elementInfoStrategy;

    VBox vbox;
    // Selected element (could be a hub, a route or nothing)
    E element;

    public NetworkElementInfo(NetworkManager manager) {
        vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);
        vbox.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: #FFFFFF;");
        vbox.setMinWidth(300);
        element = null;
        setElementInfoStrategy(new ElementInfoNoneStrategy());
        setElement(element,manager);
    }

    // Set actual strategy for the details' creation
    public void setElementInfoStrategy(ElementInfoStrategy elementInfoStrategy) {
        this.elementInfoStrategy = elementInfoStrategy;
    }

    public ElementInfoStrategy getElementInfoStrategy() {
        return elementInfoStrategy;
    }

    // Change the current selected element
    public void setElement(E element, NetworkManager manager) {
        this.element = element;
        update(manager);
    }

    public VBox getVBox() {
        return vbox;
    }

    @Override
    public void update(Object obj) {
        NetworkManager manager = (NetworkManager)obj;
        elementInfoStrategy.update(manager,vbox,element);
    }
}
