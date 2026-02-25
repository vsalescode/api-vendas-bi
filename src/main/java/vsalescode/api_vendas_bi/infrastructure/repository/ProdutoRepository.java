package vsalescode.api_vendas_bi.infrastructure.repository;

import vsalescode.api_vendas_bi.infrastructure.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}