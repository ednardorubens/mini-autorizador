package br.com.vr.authorization.core.exception;

public class SenhaInvalidaException extends TransacaoException {

    public SenhaInvalidaException() {
        super("SENHA_INVALIDA");
    }

}
