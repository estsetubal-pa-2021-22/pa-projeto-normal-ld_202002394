package pt.pa.model.exceptions;

public class IncorrectFieldException extends RuntimeException{

    public IncorrectFieldException() {
        super("Incorrect Field.");
    }

    public IncorrectFieldException(String string) {
        super(string);
    }

}
