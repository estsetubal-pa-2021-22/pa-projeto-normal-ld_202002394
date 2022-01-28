package pt.pa.model.actions;

import pt.pa.graph.Vertex;
import pt.pa.model.Hub;
import pt.pa.model.NetworkController;
import pt.pa.model.Route;

import java.util.List;
import java.util.Map;
/**
 * Class related to the Factory Pattern, working as the "Concrete Product" entity for this pattern.
 * <br>
 * Contains information of a hub removed during the program.
 *
 * @author LD_202002394
 * @version Final
 */
public class InsertHubAction implements Action {

    private final NetworkController controller;

    private final Hub hub;
    private final Map<Route,Hub> adjacentRoutes;
    private final List<Hub> hubs;

    /**
     * Method responsible for saving removed hubs.
     *
     * @param controller       controller
     * @param hub              hub
     * @param adjacentRoutes   adjacentRoutes
     * @param hubs             hubs
     *
     */
    public InsertHubAction(Object controller, Object hub, Object adjacentRoutes, Object hubs) {
        this.controller = (NetworkController) controller;
        this.hub = (Hub) hub;
        this.adjacentRoutes = (Map<Route,Hub>) adjacentRoutes;
        this.hubs = (List<Hub>) hubs;
    }

    /**
     * Method responsible for the undo functionality, regarding removed hubs.
     *
     */
    @Override
    public void undo() {
        Vertex<Hub> vertex = controller.getManager().createVertex(hub,hubs);
        for (Route route : adjacentRoutes.keySet())
            controller.getManager().createEdge(hub,adjacentRoutes.get(route),route);
        controller.getGraphView().updateAndWait();
        controller.getGraphView().setVertexPosition(vertex,hub.getCoordinates().getX(),hub.getCoordinates().getY() - 25);
    }

}
