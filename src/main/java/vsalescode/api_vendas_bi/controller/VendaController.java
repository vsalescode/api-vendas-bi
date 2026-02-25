package vsalescode.api_vendas_bi.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vsalescode.api_vendas_bi.infrastructure.entity.dtos.VendaRequestDTO;
import vsalescode.api_vendas_bi.infrastructure.entity.dtos.VendaResponseDTO;
import vsalescode.api_vendas_bi.service.VendaService;
import java.util.List;

@RestController
@RequestMapping("/vendas")
@RequiredArgsConstructor
public class VendaController {

    private final VendaService vendaService;

    @PostMapping
    public ResponseEntity<VendaResponseDTO> criar(@RequestBody VendaRequestDTO dto) {
        VendaResponseDTO response = vendaService.criarVenda(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<VendaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(vendaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(vendaService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        vendaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}