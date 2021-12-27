package pt.pa.model;

public class Action<E> {

    private Operation operation;
    private E[] element;

    public Action(Operation operation, E... element) {
        this.element = element;
        this.operation = operation;
    }

    public Operation getOperation() {
        return operation;
    }

    public E[] element() {
        return element;
    }

    public Operation getReverseOperation() {
        switch (operation) {
            case INSERT_HUB:
                return Operation.REMOVE_HUB;
            case REMOVE_HUB:
                return Operation.INSERT_HUB;
            case INSERT_ROUTE:
                return Operation.REMOVE_ROUTE;
            case REMOVE_ROUTE:
                return Operation.INSERT_ROUTE;
            default:
                return null;
        }
    }

}
