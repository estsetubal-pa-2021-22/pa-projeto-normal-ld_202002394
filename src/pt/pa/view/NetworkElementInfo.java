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

    /**
     * Constructor of the class NetworkElementInfo.
     *
     * @param manager NetworkManager
     */
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

    /**
     * Method to set actual strategy for the details' creation
     *
     * @param elementInfoStrategy ElementInfoStrategy
     */
    public void setElementInfoStrategy(ElementInfoStrategy elementInfoStrategy) {
        this.elementInfoStrategy = elementInfoStrategy;
    }

    /**
     * Method to change the current selected element
     *
     * @param element Object
     * @param manager NetworkManager
     */
    public void setElement(Object element, NetworkManager manager) {
        this.element = element;
        update(manager);
    }

    /**
     * Method to change the current selected element
     *
     * @return Returns a vbox.
     */
    public VBox getVBox() {
        return vbox;
    }

    /**
     * Method to update.
     *
     * @param obj Object
     */
    @Override
    public void update(Object obj) {
        NetworkManager manager = (NetworkManager)obj;
        elementInfoStrategy.update(manager,vbox,element);
    }
}
