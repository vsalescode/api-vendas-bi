package vsalescode.api_vendas_bi.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
import java.time.LocalDate;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
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

    @Transactional(readOnly = true)
    public Page<VendaResponseDTO> listar(Pageable pageable) {

        Page<Venda> pageVendas = vendaRepository.findAll(pageable);

        return pageVendas.map(vendaMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public VendaResponseDTO buscarPorId(Long id) {
        Venda venda = vendaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Venda não encontrada"));

        return vendaMapper.toResponseDTO(venda);
    }

    @Transactional(readOnly = true)
    public Page<VendaResponseDTO> listarPorPeriodo(
            LocalDate dataInicio,
            LocalDate dataFim,
            Pageable pageable) {

        if (dataInicio.isAfter(dataFim)) {
            throw new IllegalArgumentException("Data início não pode ser maior que data fim");
        }

        Page<Venda> page = vendaRepository
                .findByDataBetween(dataInicio, dataFim, pageable);

        return page.map(vendaMapper::toResponseDTO);
    }

    public void deletar(Long id) {
        if (!vendaRepository.existsById(id)) {
            throw new IllegalArgumentException("Venda não encontrada");
        }

        vendaRepository.deleteById(id);
    }
}