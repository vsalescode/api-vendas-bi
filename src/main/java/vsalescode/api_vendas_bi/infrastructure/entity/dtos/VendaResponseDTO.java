package vsalescode.api_vendas_bi.infrastructure.entity.dtos;


import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendaResponseDTO {

    private Long id;
    private LocalDate data;
    private String nomeCliente;
    private String cidadeCliente;
    private String nomeProduto;
    private Integer quantidade;
    private BigDecimal valorTotal;
}