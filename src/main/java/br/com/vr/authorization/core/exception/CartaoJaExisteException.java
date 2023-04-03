package br.com.vr.authorization.core.exception;

import br.com.vr.authorization.core.vo.CartaoVO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CartaoJaExisteException extends IllegalStateException {

    private final CartaoVO cartaoVO;

}
