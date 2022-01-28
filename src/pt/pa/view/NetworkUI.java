package pt.pa.view;

import javafx.scene.layout.BorderPane;
import pt.pa.model.NetworkController;

/**
 * Class contains the information/items of the panes of the application.
 * <br>
 * Class related to the MDC Pattern, working as the "View" for the pattern.
 *
 * @author LD_202002394
 * @version Final
 */

public class NetworkUI extends BorderPane {

    private final NetworkController controller;
    private final NetworkEventHandler eventHandler;

    private final NetworkMenu menuBar;
    private final NetworkElementInfo elementInfoBar;

    /**
     * Constructor of the class NetworkMenuCreate.
     *
     * @param controller NetworkController
     */
    public NetworkUI (NetworkController controller) {

        this.controller = controller;

        eventHandler = new NetworkEventHandler(this);

        menuBar = new NetworkMenu(this);
        NetworkMetrics metrics = new NetworkMetrics(controller.getManager());
        elementInfoBar = new NetworkElementInfo(controller.getManager());

        this.setCenter(controller.getGraphView());
        this.setTop(menuBar);
        this.setBottom(metrics.getHBox());
        this.setRight(elementInfoBar.getVBox());

        controller.getManager().addObservable(metrics,elementInfoBar);
    }

    /**
     * Method to get controller
     *
     * @return Returns the controller.
     */
    public NetworkController getController() {
        return this.controller;
    }

    /**
     * Method to get Event handler.
     *
     * @return Returns the Event handler.
     */
    public NetworkEventHandler getEventHandler() {
        return this.eventHandler;
    }

    /**
     * Method to get the menu bar.
     *
     * @return Returns the menu bar.
     */
    public NetworkMenu getMenuBar() {
        return this.menuBar;
    }

    /**
     * Method to get Element info bar
     *
     * @return Returns the element info bar.
     */
    public NetworkElementInfo getElementInfoBar() {
        return this.elementInfoBar;
    }

}
