package br.com.vr.authorization.usecase;

import br.com.vr.authorization.core.vo.TransacaoVO;

import java.util.function.Function;

public interface ExecutarTransacao extends Function<TransacaoVO, String> {
}
