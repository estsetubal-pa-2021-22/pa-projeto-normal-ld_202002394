package pt.pa.model.actions;

import pt.pa.graph.Vertex;
import pt.pa.model.Hub;
import pt.pa.model.NetworkController;
import pt.pa.model.Route;

import java.util.List;
import java.util.Map;

public class InsertHubAction implements Action {

    private final NetworkController controller;

    private final Hub hub;
    private final Map<Route,Hub> adjacentRoutes;
    private final List<Hub> hubs;

    public InsertHubAction(Object controller, Object hub, Object adjacentRoutes, Object hubs) {
        this.controller = (NetworkController) controller;
        this.hub = (Hub) hub;
        this.adjacentRoutes = (Map<Route,Hub>) adjacentRoutes;
        this.hubs = (List<Hub>) hubs;
    }

    @Override
    public void undo() {
        Vertex<Hub> vertex = controller.getManager().createVertex(hub,hubs);
        for (Route route : adjacentRoutes.keySet())
            controller.getManager().createEdge(hub,adjacentRoutes.get(route),route);
        controller.getGraphView().updateAndWait();
        controller.getGraphView().setVertexPosition(vertex,hub.getCoordinates().getX(),hub.getCoordinates().getY() - 25);
    }

}
