package pt.pa.model.actions;

import pt.pa.model.Hub;
import pt.pa.model.NetworkController;
import pt.pa.model.Route;
/**
 * Class related to the Factory Pattern, working as the "Concrete Product" entity for this pattern.
 * <br>
 * Contains information of a route removed during the program.
 *
 * @author LD_202002394
 * @version Final
 */
public class InsertRouteAction implements Action {

    private final NetworkController controller;
    private final Hub origin;
    private final Hub destination;
    private final Route route;
    /**
     * Method responsible for saving removed routes.
     *
     * @param controller       controller
     * @param origin           hub
     * @param destination      hub
     * @param route            route
     *
     */
    public InsertRouteAction(Object controller, Object origin, Object destination, Object route) {
        this.controller = (NetworkController) controller;
        this.origin = (Hub) origin;
        this.destination = (Hub) destination;
        this.route = (Route) route;
    }
    /**
     * Method responsible for the undo functionality, regarding removed routes.
     *
     */
    @Override
    public void undo() {
        controller.getManager().createEdge(origin,destination,route);
        controller.getGraphView().updateAndWait();
    }
}
