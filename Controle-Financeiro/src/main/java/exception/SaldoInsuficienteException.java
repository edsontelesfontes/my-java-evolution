package exception;

public class SaldoInsuficienteException extends Exception {
    public SaldoInsuficienteException() {
        super("O valor informado não pode ser maior que o saldo");
    }

    public SaldoInsuficienteException(String message) {
        super(message);
    }
}
