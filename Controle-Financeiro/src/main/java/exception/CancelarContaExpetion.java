package exception;

public class CancelarContaExpetion extends Exception{
    public CancelarContaExpetion() {
        super("Está conta já esta cancelada por motivo de ");
    }

    public CancelarContaExpetion(String message) {
        super(message);
    }
}
