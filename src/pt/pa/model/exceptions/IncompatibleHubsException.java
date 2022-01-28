package pt.pa.model.exceptions;
/**
 * Class responsible for containing the error message when the user tries to
 * insert an invalid value on the field.
 *
 * @author LD_202002394
 * @version Final
 */
public class IncompatibleHubsException extends RuntimeException {

    /**
     * Constructor of the IncompatibleHubsException class. Contains information of the exception.
     *
     */
    public IncompatibleHubsException() {
        super("The hubs selected are not joint together, please select hubs in the same component.");
    }

}
