package br.com.vr.authorization.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Cartao {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Positive
    @Column(unique = true)
    private String numeroCartao;

    @NotBlank
    @Size(min = 4, max = 8)
    private String senha;

    @NotNull
    @PositiveOrZero
    private BigDecimal saldo;

}
