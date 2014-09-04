package codebots.exceptions;

public class BadFormatException extends RuntimeException{
    public BadFormatException(String message){
        super(message);
    }
    public BadFormatException(String message, Throwable cause){
        super(message, cause);
    }

}
