package pt.pa.model.actions;

import pt.pa.model.Hub;
import pt.pa.model.NetworkController;
/**
 * Class related to the Factory Pattern, working as the "Concrete Product" entity for this pattern.
 * <br>
 * Contains information of a hub added during the program.
 *
 * @author LD_202002394
 * @version Final
 */
public class RemoveHubAction implements Action {

    private final NetworkController controller;
    private final Hub hub;

    public RemoveHubAction(Object controller, Object hub) {
        this.controller = (NetworkController) controller;
        this.hub = (Hub) hub;
    }

    @Override
    public void undo() {
        controller.getManager().removeVertex(hub);
        controller.getGraphView().updateAndWait();
    }
}
