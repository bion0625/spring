package springbook.sqlservice;

public class SqlRetrivalFailureException extends RuntimeException {
    public SqlRetrivalFailureException(String message) {
        super(message);
    }

    // SQL을 가져오는데 실패한 근본 원인을 담을 수 있도록 중첩 예외를 저장할 수 있는 생성자를 만들어 둔다.
    public SqlRetrivalFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}
