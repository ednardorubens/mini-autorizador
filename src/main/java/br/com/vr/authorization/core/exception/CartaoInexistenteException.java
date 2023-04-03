package br.com.vr.authorization.core.exception;

public class CartaoInexistenteException extends TransacaoException {

    public CartaoInexistenteException() {
        super("CARTAO_INEXISTENTE");
    }

}
