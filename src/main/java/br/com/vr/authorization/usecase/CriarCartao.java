package br.com.vr.authorization.usecase;

import br.com.vr.authorization.core.vo.CartaoVO;

import java.util.Optional;
import java.util.function.Function;

public interface CriarCartao extends Function<CartaoVO, Optional<CartaoVO>> {
}
