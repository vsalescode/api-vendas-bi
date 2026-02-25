package vsalescode.api_vendas_bi.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vsalescode.api_vendas_bi.infrastructure.entity.VendasMensal;

public interface VendasMensalRepository extends JpaRepository<VendasMensal, Long> {
}