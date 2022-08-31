package hello.jdbc.repository.ex;

public class MyDBException extends RuntimeException{
    public MyDBException() {
    }

    public MyDBException(String message) {
        super(message);
    }

    public MyDBException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyDBException(Throwable cause) {
        super(cause);   //매우중요 !! 기존예외 꼭 포함하자!!!!
    }
}
