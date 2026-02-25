package vsalescode.api_vendas_bi.infrastructure.repository;

import vsalescode.api_vendas_bi.infrastructure.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}