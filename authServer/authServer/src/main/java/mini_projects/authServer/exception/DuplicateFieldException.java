package mini_projects.authServer.exception;

public class DuplicateFieldException extends RuntimeException{
    public DuplicateFieldException(String message){
        super(message);
    }
}
