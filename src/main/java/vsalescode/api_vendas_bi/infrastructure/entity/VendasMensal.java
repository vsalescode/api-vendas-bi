package vsalescode.api_vendas_bi.infrastructure.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.math.BigDecimal;

@Entity
@Table(name = "vw_vendas_mensal")
@Getter
public class VendasMensal {

    @Id
    private Long idVenda;

    private Integer ano;
    private Integer mes;
    private Long totalVendas;
    private Long totalQuantidade;
    private BigDecimal totalFaturamento;
}