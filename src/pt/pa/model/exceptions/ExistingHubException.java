package pt.pa.model.exceptions;

public class ExistingHubException extends RuntimeException {

    public ExistingHubException() {
        super("This hub already exists!");
    }

    public ExistingHubException(String string) {
        super(string);
    }

}
