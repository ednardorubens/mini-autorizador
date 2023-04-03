package br.com.vr.authorization.usecase.impl;

import br.com.vr.authorization.core.entity.Cartao;
import br.com.vr.authorization.core.exception.SaldoInexistenteException;
import br.com.vr.authorization.gateway.repository.CartaoRepository;
import br.com.vr.authorization.usecase.ObterSaldoCartao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ObterSaldoCartaoImpl implements ObterSaldoCartao {

    private final CartaoRepository cartaoRepository;

    @Override
    public BigDecimal apply(@NotBlank String numeroCartao) {
        log.info("Obtendo o saldo do cartão {}", numeroCartao);
        return Optional.ofNullable(numeroCartao)
            .filter(StringUtils::isNotBlank)
            .flatMap(cartaoRepository::findFirstByNumeroCartao)
            .map(Cartao::getSaldo)
            .orElseThrow(SaldoInexistenteException::new);
    }

}
