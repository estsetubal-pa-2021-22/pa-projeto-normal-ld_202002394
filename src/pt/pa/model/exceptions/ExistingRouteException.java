package pt.pa.model.exceptions;
/**
 * Class responsible for containing the error message when the user tries to
 * insert a route that already exists
 *
 * @author LD_202002394
 * @version Final
 */
public class ExistingRouteException extends RuntimeException {

    public ExistingRouteException() {
        super("This route already exists!");
    }

}
