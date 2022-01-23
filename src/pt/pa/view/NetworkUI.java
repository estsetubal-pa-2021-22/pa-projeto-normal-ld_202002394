package pt.pa.view;

import javafx.scene.layout.BorderPane;
import pt.pa.model.NetworkController;

public class NetworkUI extends BorderPane {

    private final NetworkController controller;
    private final NetworkEventHandler eventHandler;

    private final NetworkMenu menuBar;
    private final NetworkElementInfo elementInfoBar;

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

    public NetworkController getController() {
        return this.controller;
    }

    public NetworkEventHandler getEventHandler() {
        return this.eventHandler;
    }

    public NetworkMenu getMenuBar() {
        return this.menuBar;
    }

    public NetworkElementInfo getElementInfoBar() {
        return this.elementInfoBar;
    }

}
