package pt.pa.model.actions;

import pt.pa.graph.Edge;
import pt.pa.model.Hub;
import pt.pa.model.NetworkController;
import pt.pa.model.Route;

public class RemoveRouteAction implements Action {

    private final NetworkController controller;
    private final Edge<Route, Hub> edge;

    public RemoveRouteAction(Object controller, Object edge) {
        this.controller = (NetworkController) controller;
        this.edge = (Edge<Route, Hub>) edge;
    }

    @Override
    public void undo() {
        controller.getManager().removeEdge(edge.element());
        controller.getGraphView().updateAndWait();
    }
}
