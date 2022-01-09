package pt.pa.model.exceptions;

public class ExistingRouteException extends RuntimeException {

    public ExistingRouteException() {
        super("This route already exists!");
    }

    public ExistingRouteException(String string) {
        super(string);
    }

}
