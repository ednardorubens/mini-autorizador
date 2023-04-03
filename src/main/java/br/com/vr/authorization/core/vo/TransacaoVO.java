package br.com.vr.authorization.core.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoVO {

    @NotBlank
    @Schema(type = "string", example = "6549873025634501")
    private String numeroCartao;

    @NotBlank
    @Schema(type = "string", example = "1234")
    private String senhaCartao;

    @NotNull
    @PositiveOrZero
    @Schema(type = "number", example = "579.63")
    private BigDecimal valor;

}
