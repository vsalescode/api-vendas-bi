# 📊 API Vendas BI

API REST desenvolvida com **Spring Boot** para gerenciamento de vendas, com suporte a relatórios e análise de dados mensais via views do banco de dados.

---

## 🚀 Tecnologias

- Java 21
- Spring Boot 4.0.3
- Spring Data JPA
- PostgreSQL
- Lombok
- MapStruct
- Bean Validation (Jakarta)

---

## 📁 Estrutura do Projeto

```
src/main/java/vsalescode/api_vendas_bi/
├── controller/
│   ├── VendaController.java
│   └── DashboardController.java
├── service/
│   ├── VendaService.java
│   └── DashboardService.java
├── infrastructure/
│   ├── entity/
│   │   ├── Cliente.java
│   │   ├── Produto.java
│   │   ├── Venda.java
│   │   ├── VendasMensal.java
│   │   └── dtos/
│   │       ├── VendaRequestDTO.java
│   │       ├── VendaResponseDTO.java
│   │       ├── ClienteRequestDTO.java
│   │       └── ProdutoRequestDTO.java
│   ├── mapper/
│   │   └── VendaMapper.java
│   ├── repository/
│   │   ├── ClienteRepository.java
│   │   ├── ProdutoRepository.java
│   │   ├── VendaRepository.java
│   │   └── VendasMensalRepository.java
│   └── data/
│       └── DataLoader.java
```

---

## ⚙️ Configuração

### Pré-requisitos

- Java 21+
- Maven 3.8+
- PostgreSQL rodando localmente

### Banco de dados

Configure as credenciais no `application.properties` ou `application.yml`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/vendas_bi
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
```

### View de vendas mensais

A entidade `VendasMensal` mapeia a view `vw_vendas_mensal`. Crie-a manualmente no banco antes de iniciar a aplicação:

```sql
CREATE OR REPLACE VIEW vw_vendas_mensal AS
SELECT
    v.id           AS id_venda,
    EXTRACT(YEAR FROM v.data)  AS ano,
    EXTRACT(MONTH FROM v.data) AS mes,
    COUNT(v.id)    AS total_vendas,
    SUM(v.quantidade) AS total_quantidade,
    SUM(v.valor_total) AS total_faturamento
FROM vendas v
GROUP BY EXTRACT(YEAR FROM v.data), EXTRACT(MONTH FROM v.data), v.id;
```

---

## ▶️ Executando o projeto

```bash
# Clone o repositório
git clone https://github.com/seu-usuario/api-vendas-bi.git
cd api-vendas-bi

# Build e execução
./mvnw spring-boot:run
```

Ao iniciar, o `DataLoader` popula automaticamente o banco com dados fictícios (5 clientes, 5 produtos e 200 vendas aleatórias), caso o banco esteja vazio.

---

## 📌 Endpoints

### Vendas — `/vendas`

| Método | Rota | Descrição |
|--------|------|-----------|
| `POST` | `/vendas` | Registra uma nova venda |
| `GET` | `/vendas` | Lista todas as vendas (paginado) |
| `GET` | `/vendas/{id}` | Busca venda por ID |
| `GET` | `/vendas/periodo?dataInicio=&dataFim=` | Lista vendas por período (paginado) |
| `DELETE` | `/vendas/{id}` | Remove uma venda |

#### Exemplo de body — POST `/vendas`

```json
{
  "clienteId": 1,
  "produtoId": 3,
  "quantidade": 2
}
```

#### Exemplo de resposta

```json
{
  "id": 201,
  "data": "2025-06-10",
  "nomeCliente": "João Silva",
  "cidadeCliente": "Fortaleza",
  "nomeProduto": "Teclado",
  "quantidade": 2,
  "valorTotal": 600.00
}
```

---

### Dashboard — `/dashboard`

| Método | Rota | Descrição |
|--------|------|-----------|
| `GET` | `/dashboard/mensal` | Retorna consolidado de vendas por mês/ano |

#### Exemplo de resposta

```json
[
  {
    "idVenda": 1,
    "ano": 2025,
    "mes": 5,
    "totalVendas": 38,
    "totalQuantidade": 97,
    "totalFaturamento": 142350.00
  }
]
```

---

## 📦 Paginação

Os endpoints que suportam paginação aceitam os parâmetros padrão do Spring:

```
GET /vendas?page=0&size=10&sort=data,desc
```

---

## 🗄️ Modelo de dados

```
Cliente (id, nome, cidade)
    |
    └──< Venda (id, data, quantidade, valorTotal, cliente_id, produto_id)
                                                        |
Produto (id, nome, preco) >─────────────────────────────┘
```

---

## 📄 Licença

Este projeto está sob a licença MIT.
