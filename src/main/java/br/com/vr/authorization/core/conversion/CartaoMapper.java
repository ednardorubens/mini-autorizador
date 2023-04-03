package br.com.vr.authorization.core.conversion;

import br.com.vr.authorization.core.entity.Cartao;
import br.com.vr.authorization.core.vo.CartaoVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedSourcePolicy = ReportingPolicy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    imports = { java.math.BigDecimal.class }
)
public interface CartaoMapper {

    @Mapping(target = "senha", source = "senha")
    @Mapping(target = "numeroCartao", source = "numeroCartao")
    CartaoVO toCartaoVO(Cartao cartao);

    @Mapping(target = "senha", source = "senha")
    @Mapping(target = "numeroCartao", source = "numeroCartao")
    @Mapping(target = "saldo", expression = "java(new BigDecimal(500d))")
    Cartao toNovoCartao(CartaoVO novoCartaoVO);

}
