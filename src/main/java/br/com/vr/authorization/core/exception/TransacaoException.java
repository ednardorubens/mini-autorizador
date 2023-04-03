package br.com.vr.authorization.core.exception;

public abstract class TransacaoException extends IllegalArgumentException {

    protected TransacaoException(String s) {
        super(s);
    }

}
