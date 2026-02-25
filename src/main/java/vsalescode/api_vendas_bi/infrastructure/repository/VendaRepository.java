package vsalescode.api_vendas_bi.infrastructure.repository;

import vsalescode.api_vendas_bi.infrastructure.entity.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaRepository extends JpaRepository<Venda, Long> {
}