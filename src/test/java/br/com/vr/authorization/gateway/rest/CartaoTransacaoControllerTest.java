package br.com.vr.authorization.gateway.rest;

import br.com.vr.authorization.core.vo.CartaoVO;
import br.com.vr.authorization.core.vo.TransacaoVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
public class CartaoTransacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(10)
    public void testCriarCartao() throws Exception {
        final CartaoVO cartaoVO = new CartaoVO("1234", "6549873025634501");

        final MockHttpServletRequestBuilder request = post("/cartoes")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(cartaoVO));

        mockMvc.perform(request)
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.senha").value("1234"))
            .andExpect(jsonPath("$.numeroCartao").value("6549873025634501"));
    }

    @Test
    @Order(20)
    public void testCartaoJaExistente() throws Exception {
        final CartaoVO cartaoVO = new CartaoVO("1234", "6549873025634501");

        final MockHttpServletRequestBuilder request = post("/cartoes")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(cartaoVO));

        mockMvc.perform(request)
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.senha").value("1234"))
            .andExpect(jsonPath("$.numeroCartao").value("6549873025634501"));
    }

    @Test
    @Order(30)
    public void testObterSaldo() throws Exception {
        final MockHttpServletRequestBuilder request = get("/cartoes/6549873025634501");

        mockMvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(content().string("500.00"));
    }

    @Test
    @Order(40)
    public void testObterSaldoCartaoNaoExiste() throws Exception {
        final MockHttpServletRequestBuilder request = get("/cartoes/6549873025634502");

        mockMvc.perform(request)
            .andExpect(status().isNotFound())
            .andExpect(content().string(""));
    }

    @Test
    @Order(50)
    public void testExecutarTransacao() throws Exception {
        final TransacaoVO transacaoVO = new TransacaoVO("6549873025634501", "1234", BigDecimal.valueOf(25.36));

        final MockHttpServletRequestBuilder request = post("/transacoes")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(transacaoVO));

        mockMvc.perform(request)
            .andExpect(status().isCreated())
            .andExpect(content().string("OK"));
    }

    @Test
    @Order(60)
    public void testExecutarTransacaoCartaoInexistente() throws Exception {
        final TransacaoVO transacaoVO = new TransacaoVO("6549873025634502", "1234", BigDecimal.valueOf(25.36));

        final MockHttpServletRequestBuilder request = post("/transacoes")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(transacaoVO));

        mockMvc.perform(request)
            .andExpect(status().isUnprocessableEntity())
            .andExpect(content().string("CARTAO_INEXISTENTE"));
    }

    @Test
    @Order(70)
    public void testExecutarTransacaoSenhaInvalida() throws Exception {
        final TransacaoVO transacaoVO = new TransacaoVO("6549873025634501", "1235", BigDecimal.valueOf(25.36));

        final MockHttpServletRequestBuilder request = post("/transacoes")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(transacaoVO));

        mockMvc.perform(request)
            .andExpect(status().isUnprocessableEntity())
            .andExpect(content().string("SENHA_INVALIDA"));
    }

    @Test
    @Order(80)
    public void testExecutarTransacaoSaldoInsuficiente() throws Exception {
        final TransacaoVO transacaoVO = new TransacaoVO("6549873025634501", "1234", BigDecimal.valueOf(750.63));

        final MockHttpServletRequestBuilder request = post("/transacoes")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(transacaoVO));

        mockMvc.perform(request)
            .andExpect(status().isUnprocessableEntity())
            .andExpect(content().string("SALDO_INSUFICIENTE"));
    }

}
