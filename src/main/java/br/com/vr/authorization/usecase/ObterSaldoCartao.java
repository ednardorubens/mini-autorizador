package br.com.vr.authorization.usecase;

import java.math.BigDecimal;
import java.util.function.Function;

public interface ObterSaldoCartao extends Function<String, BigDecimal> {
}
