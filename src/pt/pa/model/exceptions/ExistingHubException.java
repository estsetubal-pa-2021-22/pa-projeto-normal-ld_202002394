package pt.pa.model.exceptions;
/**
 * Class responsible for containing the error message when the user tries to
 * insert a hub that already exists.
 *
 * @author LD_202002394
 * @version Final
 */
public class ExistingHubException extends RuntimeException {

    public ExistingHubException() {
        super("This hub already exists!");
    }

}
