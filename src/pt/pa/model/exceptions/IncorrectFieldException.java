package pt.pa.model.exceptions;
/**
 * Class responsible for containing the error message when the user tries to
 * insert an invalid value on the field.
 *
 * @author LD_202002394
 * @version Final
 */
public class IncorrectFieldException extends RuntimeException{

    /**
     * Constructor of the IncorrectFieldException class. Contains information of the exception.
     *
     */
    public IncorrectFieldException(String string) {
        super(string);
    }

}
