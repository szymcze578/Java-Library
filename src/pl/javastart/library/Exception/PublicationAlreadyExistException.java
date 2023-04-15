package pl.javastart.library.Exception;

public class PublicationAlreadyExistException extends RuntimeException{
    public PublicationAlreadyExistException(String message) {
        super(message);
    }
}
