package pt.pa.model.exceptions;

public class NonEqualHubsException extends RuntimeException {

    public NonEqualHubsException() {
        super("Matrix doesn't match the current Hubs!");
    }

}
