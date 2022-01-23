package pt.pa.model.actions;

import pt.pa.model.Operation;

public class ActionFactory {

    public Action create(Operation operation, Object... elements) {
        switch (operation) {
            case INSERT_HUB:
                return new RemoveHubAction(elements[0],elements[1]);
            case REMOVE_HUB:
                return new InsertHubAction(elements[0],elements[1],elements[2],elements[3]);
            case INSERT_ROUTE:
                return new RemoveRouteAction(elements[0],elements[1]);
            case REMOVE_ROUTE:
                return new InsertRouteAction(elements[0],elements[1],elements[2],elements[3]);
            default:
                throw new RuntimeException("Unsupported action operation!");
        }
    }

}
