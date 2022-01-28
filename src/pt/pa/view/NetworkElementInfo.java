package pt.pa.view;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import pt.pa.model.NetworkManager;
import pt.pa.model.Observer;
import pt.pa.view.strategy.ElementInfoNoneStrategy;
import pt.pa.view.strategy.ElementInfoStrategy;

/**
 * Class responsible for generating the interface found of the right of the application,
 * containing the information of the element selected (hub, route, none).
 *
 * @author LD_202002394
 * @version Final
 */

public class NetworkElementInfo implements Observer {

    // Strategy for the element details' creation (none, hub or route)
    ElementInfoStrategy elementInfoStrategy;

    VBox vbox;
    // Selected element (could be a hub, a route or nothing)
    Object element;

    public NetworkElementInfo(NetworkManager manager) {
        vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);
        vbox.setMinWidth(300);
        vbox.getStylesheets().add("element_info.css");
        vbox.getStyleClass().add("vbox");
        element = null;
        setElementInfoStrategy(new ElementInfoNoneStrategy());
        setElement(element,manager);
    }

    // Set actual strategy for the details' creation
    public void setElementInfoStrategy(ElementInfoStrategy elementInfoStrategy) {
        this.elementInfoStrategy = elementInfoStrategy;
    }

    // Change the current selected element
    public void setElement(Object element, NetworkManager manager) {
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
