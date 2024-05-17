package springbook.sqlservice;

public class SqlNotFountException extends RuntimeException{
    public SqlNotFountException(String message) {
        super(message);
    }

    public SqlNotFountException(String message, Throwable cause) {
        super(message, cause);
    }
}
