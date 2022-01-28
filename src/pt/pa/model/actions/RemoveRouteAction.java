package pt.pa.model.actions;

import pt.pa.graph.Edge;
import pt.pa.model.Hub;
import pt.pa.model.NetworkController;
import pt.pa.model.Route;
/**
 * Class related to the Factory Pattern, working as the "Concrete Product" entity for this pattern.
 * <br>
 * Contains information a route added during the program.
 *
 * @author LD_202002394
 * @version Final
 */
public class RemoveRouteAction implements Action {

    private final NetworkController controller;
    private final Edge<Route, Hub> edge;

    /**
     * Method responsible for bringing back removed routes.
     *
     * @param controller       controller
     * @param edge             edge
     *
     */
    public RemoveRouteAction(Object controller, Object edge) {
        this.controller = (NetworkController) controller;
        this.edge = (Edge<Route, Hub>) edge;
    }

    /**
     * Method responsible for the undo functionality, regarding removed routes.
     *
     */
    @Override
    public void undo() {
        controller.getManager().removeEdge(edge.element());
        controller.getGraphView().updateAndWait();
    }
}
