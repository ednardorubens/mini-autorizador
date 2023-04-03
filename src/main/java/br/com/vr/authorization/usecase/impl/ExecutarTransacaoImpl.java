package br.com.vr.authorization.usecase.impl;

import br.com.vr.authorization.core.entity.Cartao;
import br.com.vr.authorization.core.exception.CartaoInexistenteException;
import br.com.vr.authorization.core.exception.SaldoInsuficienteException;
import br.com.vr.authorization.core.exception.SenhaInvalidaException;
import br.com.vr.authorization.core.exception.TransacaoException;
import br.com.vr.authorization.core.vo.TransacaoVO;
import br.com.vr.authorization.gateway.repository.CartaoRepository;
import br.com.vr.authorization.usecase.ExecutarTransacao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.util.Optionals;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExecutarTransacaoImpl implements ExecutarTransacao {

    private final CartaoRepository cartaoRepository;

    private static final Predicate<BigDecimal> GREATER_THEN_ZERO = bd -> bd.compareTo(BigDecimal.ZERO) > 0;

    @Override
    public String apply(@NotNull final TransacaoVO transacaoVO) {
        log.info("Executando {}", transacaoVO);

        final Cartao cartao = validarCartao(transacaoVO);

        validarSenha(cartao.getSenha(), transacaoVO.getSenhaCartao());

        final BigDecimal novoSaldo = validarSaldo(cartao.getSaldo(), transacaoVO.getValor());

        atualizarSaldo(cartao, novoSaldo);

        return "OK";
    }

    private Cartao validarCartao(final TransacaoVO transacaoVO) {
        return notBlank(transacaoVO.getNumeroCartao())
            .flatMap(cartaoRepository::findFirstByNumeroCartao)
            .orElseThrow(CartaoInexistenteException::new);
    }

    private void validarSenha(final String senhaCartao, final String senhaTransacao) {
        validarCampo(
            senhaCartao,
            senhaTransacao,
            this::notBlank,
            String::equalsIgnoreCase,
            Boolean.TRUE::equals,
            SenhaInvalidaException::new
        );
    }

    private BigDecimal validarSaldo(final BigDecimal saldo, final BigDecimal valor) {
        return validarCampo(
            saldo,
            valor,
            this::greaterThenZero,
            BigDecimal::subtract,
            GREATER_THEN_ZERO,
            SaldoInsuficienteException::new
        );
    }

    private <T, R> R validarCampo(
            final T vlr1,
            final T vlr2,
            final Function<T, Optional<T>> notNull,
            final BiFunction<T, T, R> transform,
            final Predicate<R> filter,
            final Supplier<TransacaoException> ex) {
        return Optionals.mapIfAllPresent(
            notNull.apply(vlr1),
            notNull.apply(vlr2),
            transform
        ).filter(filter)
        .orElseThrow(ex);
    }

    private void atualizarSaldo(final Cartao cartao, final BigDecimal novoSaldo) {
        cartao.setSaldo(novoSaldo);
        cartaoRepository.save(cartao);
    }

    private Optional<String> notBlank(final String str) {
        return Optional.ofNullable(str)
            .filter(StringUtils::isNotBlank);
    }

    private Optional<BigDecimal> greaterThenZero(final BigDecimal vlr) {
        return Optional.ofNullable(vlr)
            .filter(GREATER_THEN_ZERO);
    }

}
