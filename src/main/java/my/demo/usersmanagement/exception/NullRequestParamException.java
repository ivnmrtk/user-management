package my.demo.usersmanagement.exception;

public class NullRequestParamException extends RuntimeException {

    public NullRequestParamException(String message) {
        super(message);
    }
}
