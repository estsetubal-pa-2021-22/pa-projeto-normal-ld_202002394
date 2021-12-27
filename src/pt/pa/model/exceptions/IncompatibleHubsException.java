package pt.pa.model.exceptions;

public class IncompatibleHubsException extends RuntimeException {

    public IncompatibleHubsException() {
        super("The hubs selected are not joint together, please select hubs in the same component.");
    }

    public IncompatibleHubsException(String string) {
        super(string);
    }

}
