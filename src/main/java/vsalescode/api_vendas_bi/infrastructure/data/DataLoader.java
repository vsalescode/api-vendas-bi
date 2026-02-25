package vsalescode.api_vendas_bi.infrastructure.data;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import vsalescode.api_vendas_bi.infrastructure.entity.Cliente;
import vsalescode.api_vendas_bi.infrastructure.entity.Produto;
import vsalescode.api_vendas_bi.infrastructure.entity.Venda;
import vsalescode.api_vendas_bi.infrastructure.repository.ClienteRepository;
import vsalescode.api_vendas_bi.infrastructure.repository.ProdutoRepository;
import vsalescode.api_vendas_bi.infrastructure.repository.VendaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final VendaRepository vendaRepository;

    @Override
    public void run(String... args) {

        if (clienteRepository.count() > 0) {
            return; // Não duplica ao reiniciar
        }

        Random random = new Random();


        List<Cliente> clientes = List.of(
                Cliente.builder().nome("João Silva").cidade("Fortaleza").build(),
                Cliente.builder().nome("Maria Souza").cidade("Recife").build(),
                Cliente.builder().nome("Carlos Lima").cidade("São Paulo").build(),
                Cliente.builder().nome("Ana Costa").cidade("Salvador").build(),
                Cliente.builder().nome("Pedro Alves").cidade("Rio de Janeiro").build()
        );

        clienteRepository.saveAll(clientes);

        // Produtos
        List<Produto> produtos = List.of(
                Produto.builder().nome("Notebook").preco(new BigDecimal("3500")).build(),
                Produto.builder().nome("Mouse").preco(new BigDecimal("150")).build(),
                Produto.builder().nome("Teclado").preco(new BigDecimal("300")).build(),
                Produto.builder().nome("Monitor").preco(new BigDecimal("1200")).build(),
                Produto.builder().nome("Headset").preco(new BigDecimal("450")).build()
        );

        produtoRepository.saveAll(produtos);

        // Vendas
        for (int i = 0; i < 200; i++) {

            Cliente cliente = clientes.get(random.nextInt(clientes.size()));
            Produto produto = produtos.get(random.nextInt(produtos.size()));

            int quantidade = random.nextInt(5) + 1;

            LocalDate dataAleatoria = LocalDate.now()
                    .minusDays(random.nextInt(180));

            BigDecimal valorTotal = produto.getPreco()
                    .multiply(BigDecimal.valueOf(quantidade));

            Venda venda = Venda.builder()
                    .cliente(cliente)
                    .produto(produto)
                    .quantidade(quantidade)
                    .data(dataAleatoria)
                    .valorTotal(valorTotal)
                    .build();

            vendaRepository.save(venda);
        }

        System.out.println("Banco populado, verifique o Postgres");
    }
}