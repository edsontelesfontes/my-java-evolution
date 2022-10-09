package exception;

public class TransferenciaDestinoNuloException extends Exception{
    public TransferenciaDestinoNuloException() {
        super("Conta Inválida");
    }

    public TransferenciaDestinoNuloException(String message) {
        super(message);
    }
}
