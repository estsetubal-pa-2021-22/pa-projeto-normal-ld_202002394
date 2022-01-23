package pt.pa.model.actions;

import pt.pa.model.Hub;
import pt.pa.model.NetworkController;

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
