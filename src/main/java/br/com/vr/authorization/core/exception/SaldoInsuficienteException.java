package br.com.vr.authorization.core.exception;

public class SaldoInsuficienteException extends TransacaoException {

    public SaldoInsuficienteException() {
        super("SALDO_INSUFICIENTE");
    }

}
