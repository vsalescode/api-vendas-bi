package vsalescode.api_vendas_bi.infrastructure.mapper;




import vsalescode.api_vendas_bi.infrastructure.entity.Venda;
import vsalescode.api_vendas_bi.infrastructure.entity.dtos.VendaResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VendaMapper {

    @Mapping(source = "cliente.nome", target = "nomeCliente")
    @Mapping(source = "cliente.cidade", target = "cidadeCliente")
    @Mapping(source = "produto.nome", target = "nomeProduto")


    VendaResponseDTO toResponseDTO(Venda venda);
    List<VendaResponseDTO> toResponseList(List<Venda> vendas);
}
