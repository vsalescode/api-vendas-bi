package vsalescode.api_vendas_bi.infrastructure.repository;

import org.springframework.data.domain.Page;
import vsalescode.api_vendas_bi.infrastructure.entity.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import org.springframework.data.domain.Pageable;

public interface VendaRepository extends JpaRepository<Venda, Long> {

    Page<Venda> findByDataBetween(
            LocalDate dataInicio,
            LocalDate dataFim,
            Pageable pageable
    );
}