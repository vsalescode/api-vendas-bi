package vsalescode.api_vendas_bi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vsalescode.api_vendas_bi.infrastructure.entity.VendasMensal;
import vsalescode.api_vendas_bi.infrastructure.repository.VendasMensalRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final VendasMensalRepository repository;

    public List<VendasMensal> listarMensal() {
        return repository.findAll();
    }
}