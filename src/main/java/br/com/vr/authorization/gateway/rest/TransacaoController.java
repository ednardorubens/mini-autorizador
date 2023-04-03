package br.com.vr.authorization.gateway.rest;

import br.com.vr.authorization.core.vo.TransacaoVO;
import br.com.vr.authorization.usecase.ExecutarTransacao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transacoes")
public class TransacaoController {

    private final ExecutarTransacao executarTransacao;

    @Operation(summary = "Realizar uma transação")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Transação OK", content = @Content(schema = @Schema(type = "string", example = "OK"))),
        @ApiResponse(responseCode = "422", description = "Transação com erro", content = {
            @Content(schema = @Schema(type = "string", example = "SALDO_INSUFICIENTE | SENHA_INVALIDA | CARTAO_INEXISTENTE"))
        })
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public String executarTransacao(@RequestBody final TransacaoVO transacaoVO) {
        return executarTransacao.apply(transacaoVO);
    }

}
