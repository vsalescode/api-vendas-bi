package vsalescode.api_vendas_bi.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vsalescode.api_vendas_bi.infrastructure.entity.VendasMensal;
import vsalescode.api_vendas_bi.service.DashboardService;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService service;

    @GetMapping("/mensal")
    public List<VendasMensal> listarMensal() {
        return service.listarMensal();
    }
}