package br.com.vr.authorization.core.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SaldoInexistenteException extends IllegalStateException {
}
