<div align="center">

# 📊 API Vendas BI

**API REST para gerenciamento de vendas com análise de dados e relatórios mensais**

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.3-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![Lombok](https://img.shields.io/badge/Lombok-BC4521?style=for-the-badge&logo=lombok&logoColor=white)

</div>

---

## 📋 Sumário

- [Sobre o Projeto](#-sobre-o-projeto)
- [Tecnologias](#-tecnologias)
- [Arquitetura](#-arquitetura)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Modelo de Dados](#-modelo-de-dados)
- [Pré-requisitos](#-pré-requisitos)
- [Configuração e Instalação](#-configuração-e-instalação)
- [Endpoints](#-endpoints)
- [Paginação e Ordenação](#-paginação-e-ordenação)
- [Validações e Erros](#-validações-e-erros)
- [Dados de Seed](#-dados-de-seed)

---

## 💡 Sobre o Projeto

A **API Vendas BI** é uma API RESTful construída com Spring Boot para gerenciar o ciclo de vendas de uma empresa, oferecendo:

- Cadastro e consulta de vendas com paginação
- Filtragem de vendas por período
- Dashboard com consolidado mensal de faturamento via **database view**
- Carga automática de dados fictícios para testes

A camada de dashboard consome diretamente a view `vw_vendas_mensal` do PostgreSQL, mantendo a lógica analítica no banco e a API focada em expor os dados.

---

## 🚀 Tecnologias

| Tecnologia | Versão | Finalidade |
|---|---|---|
| Java | 21 | Linguagem principal |
| Spring Boot | 4.0.3 | Framework web e IoC |
| Spring Data JPA | — | Persistência e repositórios |
| PostgreSQL | — | Banco de dados relacional |
| Lombok | 1.18.30 | Redução de boilerplate |
| MapStruct | 1.5.5 | Mapeamento entre entidades e DTOs |
| Bean Validation | — | Validação de requisições |
| Maven | 3.8+ | Gerenciamento de dependências e build |

---

## 🏗️ Arquitetura

O projeto segue uma arquitetura em camadas com separação clara de responsabilidades:

```
┌─────────────────────────────────────────────────────────┐
│                      HTTP Client                        │
└───────────────────────┬─────────────────────────────────┘
                        │
┌───────────────────────▼─────────────────────────────────┐
│                   Controller Layer                      │
│           VendaController  │  DashboardController       │
└───────────────────────┬─────────────────────────────────┘
                        │
┌───────────────────────▼─────────────────────────────────┐
│                    Service Layer                        │
│              VendaService  │  DashboardService          │
└───────────────────────┬─────────────────────────────────┘
                        │
┌───────────────────────▼─────────────────────────────────┐
│                 Infrastructure Layer                    │
│   Entities │ DTOs │ Mapper │ Repositories │ DataLoader  │
└───────────────────────┬─────────────────────────────────┘
                        │
┌───────────────────────▼─────────────────────────────────┐
│                     PostgreSQL                          │
│         Tables: clientes, produtos, vendas              │
│         View:   vw_vendas_mensal                        │
└─────────────────────────────────────────────────────────┘
```

---

## 📁 Estrutura do Projeto

```
src/main/java/vsalescode/api_vendas_bi/
│
├── controller/
│   ├── VendaController.java         # Endpoints CRUD de vendas
│   └── DashboardController.java     # Endpoint de relatório mensal
│
├── service/
│   ├── VendaService.java            # Regras de negócio das vendas
│   └── DashboardService.java        # Serviço de consolidado mensal
│
└── infrastructure/
    ├── entity/
    │   ├── Cliente.java             # Entidade JPA — tabela clientes
    │   ├── Produto.java             # Entidade JPA — tabela produtos
    │   ├── Venda.java               # Entidade JPA — tabela vendas
    │   ├── VendasMensal.java        # Entidade JPA — view vw_vendas_mensal
    │   └── dtos/
    │       ├── VendaRequestDTO.java      # Payload de entrada para vendas
    │       ├── VendaResponseDTO.java     # Payload de saída para vendas
    │       ├── ClienteRequestDTO.java    # Payload de entrada para clientes
    │       └── ProdutoRequestDTO.java    # Payload de entrada para produtos
    │
    ├── mapper/
    │   └── VendaMapper.java         # MapStruct: Venda <-> VendaResponseDTO
    │
    ├── repository/
    │   ├── ClienteRepository.java
    │   ├── ProdutoRepository.java
    │   ├── VendaRepository.java          # Inclui query por período
    │   └── VendasMensalRepository.java
    │
    └── data/
        └── DataLoader.java          # Seed automático ao iniciar a aplicação
```

---

## 🗄️ Modelo de Dados

### Diagrama de entidades

```
┌──────────────────┐          ┌─────────────────────────────────┐
│    clientes      │          │             vendas              │
│──────────────────│          │─────────────────────────────────│
│ id        BIGINT │◄─────────┤ id            BIGINT            │
│ nome      VARCHAR│  1 : N   │ data          DATE              │
│ cidade    VARCHAR│          │ quantidade    INTEGER           │
└──────────────────┘          │ valor_total   NUMERIC(10,2)     │
                              │ cliente_id    BIGINT  (FK)      │
┌──────────────────┐          │ produto_id    BIGINT  (FK)      │
│    produtos      │          └─────────────────────────────────┘
│──────────────────│                          ▲
│ id        BIGINT │◄─────────────────────────┘
│ nome      VARCHAR│          1 : N
│ preco     NUMERIC│
└──────────────────┘

┌──────────────────────────────────────────┐
│           vw_vendas_mensal  (VIEW)       │
│──────────────────────────────────────────│
│ id_venda           BIGINT                │
│ ano                INTEGER               │
│ mes                INTEGER               │
│ total_vendas       BIGINT                │
│ total_quantidade   BIGINT                │
│ total_faturamento  NUMERIC               │
└──────────────────────────────────────────┘
```

---

## 🔧 Pré-requisitos

Antes de começar, certifique-se de ter instalado:

- [Java 21+](https://adoptium.net/)
- [Maven 3.8+](https://maven.apache.org/download.cgi)
- [PostgreSQL 14+](https://www.postgresql.org/download/)

---

## ⚙️ Configuração e Instalação

### 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/api-vendas-bi.git
cd api-vendas-bi
```

### 2. Crie o banco de dados

```sql
CREATE DATABASE vendas_bi;
```

### 3. Crie a view de vendas mensais

> ⚠️ **A view deve ser criada manualmente antes de iniciar a aplicação**, pois o Hibernate não a gerencia automaticamente.

```sql
CREATE OR REPLACE VIEW vw_vendas_mensal AS
SELECT
    v.id                        AS id_venda,
    EXTRACT(YEAR  FROM v.data)  AS ano,
    EXTRACT(MONTH FROM v.data)  AS mes,
    COUNT(v.id)                 AS total_vendas,
    SUM(v.quantidade)           AS total_quantidade,
    SUM(v.valor_total)          AS total_faturamento
FROM vendas v
GROUP BY
    v.id,
    EXTRACT(YEAR  FROM v.data),
    EXTRACT(MONTH FROM v.data);
```

### 4. Configure as propriedades da aplicação

Edite o arquivo `src/main/resources/application.properties`:

```properties
# Datasource
spring.datasource.url=jdbc:postgresql://localhost:5432/vendas_bi
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

### 5. Execute a aplicação

```bash
./mvnw spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

---

## 📌 Endpoints

### 🛒 Vendas — `/vendas`

#### `POST /vendas` — Registrar nova venda

Calcula o `valorTotal` automaticamente com base no preço do produto × quantidade.

**Request Body:**
```json
{
  "clienteId": 1,
  "produtoId": 3,
  "quantidade": 2
}
```

**Validações:**
- `clienteId` — obrigatório
- `produtoId` — obrigatório
- `quantidade` — obrigatório e maior que zero

**Response `200 OK`:**
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

#### `GET /vendas` — Listar todas as vendas (paginado)

```
GET /vendas?page=0&size=10&sort=data,desc
```

**Response `200 OK`:**
```json
{
  "content": [
    {
      "id": 201,
      "data": "2025-06-10",
      "nomeCliente": "João Silva",
      "cidadeCliente": "Fortaleza",
      "nomeProduto": "Teclado",
      "quantidade": 2,
      "valorTotal": 600.00
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalElements": 200,
  "totalPages": 20,
  "last": false
}
```

---

#### `GET /vendas/{id}` — Buscar venda por ID

```
GET /vendas/1
```

**Response `200 OK`:** mesmo formato do objeto de venda acima.

---

#### `GET /vendas/periodo` — Listar vendas por período (paginado)

```
GET /vendas/periodo?dataInicio=2025-01-01&dataFim=2025-06-30&page=0&size=10
```

| Parâmetro | Tipo | Formato | Obrigatório |
|---|---|---|---|
| `dataInicio` | `LocalDate` | `yyyy-MM-dd` | ✅ |
| `dataFim` | `LocalDate` | `yyyy-MM-dd` | ✅ |
| `page` | `int` | — | ❌ (default `0`) |
| `size` | `int` | — | ❌ (default `20`) |
| `sort` | `string` | campo,direção | ❌ |

> ⚠️ Se `dataInicio` for posterior a `dataFim`, a API retorna erro com a mensagem: `"Data início não pode ser maior que data fim"`.

---

#### `DELETE /vendas/{id}` — Remover venda

```
DELETE /vendas/1
```

**Response `204 No Content`** em caso de sucesso.

---

### 📊 Dashboard — `/dashboard`

#### `GET /dashboard/mensal` — Consolidado mensal de vendas

Retorna os dados diretamente da view `vw_vendas_mensal`, agregados por ano e mês.

```
GET /dashboard/mensal
```

**Response `200 OK`:**
```json
[
  {
    "idVenda": 1,
    "ano": 2025,
    "mes": 1,
    "totalVendas": 38,
    "totalQuantidade": 97,
    "totalFaturamento": 142350.00
  },
  {
    "idVenda": 2,
    "ano": 2025,
    "mes": 2,
    "totalVendas": 42,
    "totalQuantidade": 110,
    "totalFaturamento": 165200.00
  }
]
```

---

## 📦 Paginação e Ordenação

Todos os endpoints de listagem suportam os parâmetros padrão do Spring Data:

| Parâmetro | Descrição | Exemplo |
|---|---|---|
| `page` | Número da página (zero-based) | `page=0` |
| `size` | Quantidade de itens por página | `size=20` |
| `sort` | Campo e direção de ordenação | `sort=data,desc` |

**Exemplos:**

```bash
# Primeira página, 10 itens, ordenado por data decrescente
GET /vendas?page=0&size=10&sort=data,desc

# Segunda página, 5 itens, ordenado por valorTotal
GET /vendas?page=1&size=5&sort=valorTotal,desc
```

---

## ⚠️ Validações e Erros

### Validação de entrada (`POST /vendas`)

| Campo | Regra | Mensagem |
|---|---|---|
| `clienteId` | Não pode ser nulo | `"Cliente é obrigatório"` |
| `produtoId` | Não pode ser nulo | `"Produto é obrigatório"` |
| `quantidade` | Não pode ser nulo e > 0 | `"Quantidade é obrigatória"` / `"Quantidade deve ser maior que zero"` |

### Erros de negócio

| Situação | Mensagem lançada |
|---|---|
| Cliente não encontrado | `"Cliente não encontrado"` |
| Produto não encontrado | `"Produto não encontrado"` |
| Venda não encontrada | `"Venda não encontrada"` |
| `dataInicio` > `dataFim` | `"Data início não pode ser maior que data fim"` |

---

## 🌱 Dados de Seed

Ao iniciar a aplicação com o banco vazio, o `DataLoader` popula automaticamente:

| Entidade | Quantidade | Detalhes |
|---|---|---|
| Clientes | 5 | João Silva, Maria Souza, Carlos Lima, Ana Costa, Pedro Alves |
| Produtos | 5 | Notebook (R$ 3.500), Mouse (R$ 150), Teclado (R$ 300), Monitor (R$ 1.200), Headset (R$ 450) |
| Vendas | 200 | Geradas aleatoriamente nos últimos 180 dias |

> A carga é executada apenas uma vez. Se o banco já contiver clientes, o seed é ignorado automaticamente.

---

## 📄 Licença

Este projeto está sob a licença [MIT](LICENSE).

---

<div align="center">
  Desenvolvido por <a href="https://github.com/vsalescode">vsalescode</a>
</div>
