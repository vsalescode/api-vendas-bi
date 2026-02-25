package vsalescode.api_vendas_bi.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vsalescode.api_vendas_bi.infrastructure.entity.Cliente;
import vsalescode.api_vendas_bi.infrastructure.entity.Produto;
import vsalescode.api_vendas_bi.infrastructure.entity.Venda;
import vsalescode.api_vendas_bi.infrastructure.entity.dtos.VendaRequestDTO;
import vsalescode.api_vendas_bi.infrastructure.entity.dtos.VendaResponseDTO;
import vsalescode.api_vendas_bi.infrastructure.mapper.VendaMapper;
import vsalescode.api_vendas_bi.infrastructure.repository.ClienteRepository;
import vsalescode.api_vendas_bi.infrastructure.repository.ProdutoRepository;
import vsalescode.api_vendas_bi.infrastructure.repository.VendaRepository;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final VendaMapper vendaMapper;

    public VendaResponseDTO criarVenda(VendaRequestDTO dto) {

        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        Produto produto = produtoRepository.findById(dto.getProdutoId())
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

        BigDecimal valorTotal = produto.getPreco()
                .multiply(BigDecimal.valueOf(dto.getQuantidade()));

        Venda venda = Venda.builder()
                .data(java.time.LocalDate.now())
                .quantidade(dto.getQuantidade())
                .valorTotal(valorTotal)
                .cliente(cliente)
                .produto(produto)
                .build();

        Venda vendaSalva = vendaRepository.save(venda);

        return vendaMapper.toResponseDTO(vendaSalva);
    }

    public List<VendaResponseDTO> listarTodas() {
        List<Venda> vendas = vendaRepository.findAll();
        return vendaMapper.toResponseList(vendas);
    }

    public VendaResponseDTO buscarPorId(Long id) {
        Venda venda = vendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada"));

        return vendaMapper.toResponseDTO(venda);
    }

    public void deletar(Long id) {
        vendaRepository.deleteById(id);
    }
}