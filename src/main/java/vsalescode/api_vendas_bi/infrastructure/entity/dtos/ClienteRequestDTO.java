package vsalescode.api_vendas_bi.infrastructure.entity.dtos;

import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteRequestDTO {

    private String nome;
    private String cidade;
}
