package exception;

public class DataInvalidaException extends Exception{
    public DataInvalidaException() {
        super("A data inicial não pode ser depois da data estipulada !");
    }

    public DataInvalidaException(String message) {
        super(message);
    }
}
