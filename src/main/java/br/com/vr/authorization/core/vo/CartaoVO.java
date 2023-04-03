package br.com.vr.authorization.core.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartaoVO implements Serializable {

    @Schema(type = "string", example = "1234")
    private String senha;

    @Schema(type = "string", example = "6549873025634501")
    private String numeroCartao;

}
