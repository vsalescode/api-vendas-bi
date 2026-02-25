package vsalescode.api_vendas_bi.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vsalescode.api_vendas_bi.infrastructure.entity.dtos.VendaRequestDTO;
import vsalescode.api_vendas_bi.infrastructure.entity.dtos.VendaResponseDTO;
import vsalescode.api_vendas_bi.service.VendaService;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

@RestController
@RequestMapping("/vendas")
@RequiredArgsConstructor
public class VendaController {

    private final VendaService vendaService;

    @PostMapping
    public ResponseEntity<VendaResponseDTO> criar(@Valid @RequestBody VendaRequestDTO dto) {
        VendaResponseDTO response = vendaService.criarVenda(dto);
        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<Page<VendaResponseDTO>> listar(Pageable pageable) {
        return ResponseEntity.ok(vendaService.listar(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(vendaService.buscarPorId(id));
    }

    @GetMapping("/periodo")
    public ResponseEntity<Page<VendaResponseDTO>> listarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate dataInicio,

            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate dataFim,

            Pageable pageable) {

        return ResponseEntity.ok(
                vendaService.listarPorPeriodo(dataInicio, dataFim, pageable)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        vendaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}