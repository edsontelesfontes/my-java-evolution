package exception;

public class ValorInsuficienteException extends Exception{
    public ValorInsuficienteException() {
        super("O valor para depósito deve ser maior que zero");
    }

    public ValorInsuficienteException(String message) {
        super(message);
    }
}
