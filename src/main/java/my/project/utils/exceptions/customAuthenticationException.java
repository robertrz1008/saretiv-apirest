package my.project.utils.exceptions;

public class CustomAuthenticationException extends RuntimeException{

    public CustomAuthenticationException(String message) {
        super(message);
    }
}
