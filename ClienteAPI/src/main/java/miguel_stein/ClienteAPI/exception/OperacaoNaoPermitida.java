package miguel_stein.ClienteAPI.exception;

public class OperacaoNaoPermitida extends RuntimeException {
    public OperacaoNaoPermitida(String message) {
        super(message);
    }
}
