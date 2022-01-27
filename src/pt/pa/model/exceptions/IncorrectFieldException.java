package pt.pa.model.exceptions;
/**
 * Class responsible for containing the error message when the user tries to
 * insert an invalid value on the field.
 *
 * @author LD_202002394
 * @version Final
 */
public class IncorrectFieldException extends RuntimeException{

    public IncorrectFieldException(String string) {
        super(string);
    }

}
