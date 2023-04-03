package br.com.vr.authorization.usecase.impl;

import br.com.vr.authorization.core.conversion.CartaoMapper;
import br.com.vr.authorization.core.exception.CartaoJaExisteException;
import br.com.vr.authorization.core.vo.CartaoVO;
import br.com.vr.authorization.gateway.repository.CartaoRepository;
import br.com.vr.authorization.usecase.CriarCartao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CriarCartaoImpl implements CriarCartao {

    private final CartaoMapper cartaoMapper;
    private final CartaoRepository cartaoRepository;

    @Override
    public Optional<CartaoVO> apply(@NotNull final CartaoVO novoCartaoVO) {
        log.info("Criando {}", novoCartaoVO);
        try {
            return Optional.ofNullable(novoCartaoVO)
                .map(cartaoMapper::toNovoCartao)
                .map(cartaoRepository::save)
                .map(cartaoMapper::toCartaoVO);
        } catch (DataIntegrityViolationException ex) {
            throw new CartaoJaExisteException(novoCartaoVO);
        }
    }

}
