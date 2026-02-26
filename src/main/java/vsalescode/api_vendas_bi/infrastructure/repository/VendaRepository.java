package vsalescode.api_vendas_bi.infrastructure.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.EntityGraph;
import vsalescode.api_vendas_bi.infrastructure.entity.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

public interface VendaRepository extends JpaRepository<Venda, Long> {

    @Override
    @EntityGraph(attributePaths = {"cliente", "produto"})
    Page<Venda> findAll(Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {"cliente", "produto"})
    Optional<Venda> findById(Long id);

    @EntityGraph(attributePaths = {"cliente", "produto"})
    Page<Venda> findByDataBetween(
            LocalDate dataInicio,
            LocalDate dataFim,
            Pageable pageable
    );
}
