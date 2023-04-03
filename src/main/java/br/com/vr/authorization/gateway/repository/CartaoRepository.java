package br.com.vr.authorization.gateway.repository;

import br.com.vr.authorization.core.entity.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {

    Optional<Cartao> findFirstByNumeroCartao(String numeroCartao);

}
