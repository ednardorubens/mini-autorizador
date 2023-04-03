package br.com.vr.authorization.gateway.rest;

import br.com.vr.authorization.core.vo.CartaoVO;
import br.com.vr.authorization.usecase.CriarCartao;
import br.com.vr.authorization.usecase.ObterSaldoCartao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cartoes")
public class CartaoController {

    private final CriarCartao criarCartao;
    private final ObterSaldoCartao obterSaldoCartao;

    @Operation(summary = "Criar um novo cartão")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cartão criado"),
        @ApiResponse(responseCode = "422", description = "Cartão já existe")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Optional<CartaoVO> criarCartao(@RequestBody final CartaoVO novoCartaoVO) {
        return criarCartao.apply(novoCartaoVO);
    }

    @Operation(summary = "Retornar o saldo do cartão")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cartão encontrado", content = @Content(schema = @Schema(type = "number", example = "579.63"))),
        @ApiResponse(responseCode = "404", description = "Cartão não encontrado", content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping(path = "/{numeroCartao}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BigDecimal obterSaldo(@PathVariable("numeroCartao") final String numeroCartao) {
        return obterSaldoCartao.apply(numeroCartao);
    }

}
