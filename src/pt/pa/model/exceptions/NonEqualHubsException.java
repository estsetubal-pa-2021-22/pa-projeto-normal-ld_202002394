package pt.pa.model.exceptions;
/**
 * Class responsible for containing the error message when the user tries to
 * import a route's matrix with a different amount of hubs compared to the actual graph.
 *
 * @author LD_202002394
 * @version Final
 */
public class NonEqualHubsException extends RuntimeException {

    public NonEqualHubsException() {
        super("Matrix doesn't match the current Hubs!");
    }

}
