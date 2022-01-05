package pt.pa.view;

import javafx.scene.layout.BorderPane;
import pt.pa.graph.Edge;
import pt.pa.model.NetworkController;

public class NetworkUI extends BorderPane {

    private NetworkController controller;
    private NetworkEventHandler eventHandler;

    private NetworkMenu menuBar;
    private NetworkMetrics metrics;
    private NetworkElementInfo elementInfoBar;

    public NetworkUI (NetworkController controller) {
        this.controller = controller;

        eventHandler = new NetworkEventHandler(this);

        menuBar = new NetworkMenu(this);
        metrics = new NetworkMetrics(controller.getManager());
        elementInfoBar = new NetworkElementInfo(controller.getManager());

        this.setLeft(controller.getGraphView());
        this.setTop(menuBar);
        this.setBottom(metrics.getHBox());
        this.setRight(elementInfoBar.getVBox());

        controller.getManager().addObservable(metrics,elementInfoBar);
    }

    public NetworkController getController() {
        return this.controller;
    }

    public void updateMetrics() {
        metrics.update(controller.getManager());
    }

    public NetworkEventHandler getEventHandler() {
        return this.eventHandler;
    }

    public NetworkMenu getMenuBar() {
        return this.menuBar;
    }

    public NetworkMetrics getMetrics() {
        return this.metrics;
    }

    public NetworkElementInfo getElementInfoBar() {
        return this.elementInfoBar;
    }

    /*
    public void setElementInfoBar(NetworkElementInfo elementInfo) {
        this.elementInfoBar = elementInfo;
        this.setRight(elementInfoBar);
    }
    */

    public void updateGraphStyle() {
        for (Edge edge : controller.getManager().getGraph().edges())
            controller.getGraphView().getStylableEdge(edge).setStyle("-fx-stroke: #FF6D66; -fx-stroke-width: 1;");
    }

}
