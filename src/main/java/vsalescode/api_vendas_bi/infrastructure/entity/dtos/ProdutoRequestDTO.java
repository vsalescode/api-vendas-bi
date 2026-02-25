package vsalescode.api_vendas_bi.infrastructure.entity.dtos;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdutoRequestDTO {

    private String nome;
    private BigDecimal preco;
}
