# API de Créditos Fiscais

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.java.net/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.12-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.11.0-blue.svg)](https://maven.apache.org/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-13+-blue.svg)](https://www.postgresql.org/)
[![Flyway](https://img.shields.io/badge/Flyway-9.22.3-yellow.svg)](https://flywaydb.org/)

## 📋 Descrição

API REST desenvolvida em Spring Boot para consulta de créditos fiscais. O sistema permite consultar créditos constituídos associados a Notas Fiscais de Serviço Eletrônica (NFS-e) e fornece funcionalidades para busca e validação de dados fiscais.

## 🚀 Tecnologias Utilizadas

- **Java 17** - Linguagem de programação
- **Spring Boot 3.3.12** - Framework para desenvolvimento de aplicações Java
- **Spring Data JPA** - Persistência de dados
- **PostgreSQL** - Banco de dados relacional
- **Flyway** - Migração de banco de dados
- **MapStruct** - Mapeamento entre objetos
- **Lombok** - Redução de código boilerplate
- **Maven** - Gerenciamento de dependências

## 📁 Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/desafio/credito/
│   │   ├── controller/          # Controladores REST
│   │   ├── service/             # Lógica de negócio
│   │   ├── repository/          # Acesso a dados
│   │   ├── entity/              # Entidades JPA
│   │   ├── dto/                 # Objetos de transferência
│   │   ├── mapper/              # Mapeadores MapStruct
│   │   ├── exception/           # Tratamento de exceções
│   │   └── JavawebApplication.java
│   └── resources/
│       ├── application.yml      # Configurações da aplicação
│       └── db/migration/        # Scripts de migração Flyway
├── README.md                    # Documentação principal
├── GIT_SETUP.md                 # Guia de primeira publicação no Git
├── setup.sh                     # Script de setup (Linux/macOS)
├── setup.bat                    # Script de setup (Windows)
├── env.example                  # Exemplo de variáveis de ambiente
└── pom.xml                      # Configuração do Maven
```

## 🛠️ Pré-requisitos

- **Java 17** ou superior
- **Maven 3.6+**
- **PostgreSQL 13+**
- **Git**

## ⚙️ Configuração do Ambiente

### 🚀 Setup Automático (Recomendado)

#### Linux/macOS:

```bash
# Tornar o script executável (se necessário)
chmod +x setup.sh

# Executar o setup
./setup.sh
```

#### Windows:

```cmd
# Executar o script de setup
setup.bat
```

### 🔧 Setup Manual

#### 1. Clone o repositório

```bash
git clone <url-do-repositorio>
cd backend
```

#### 2. Configuração do Banco de Dados

Crie um banco de dados PostgreSQL:

```sql
CREATE DATABASE postgres;
```

#### 3. Configuração das Variáveis de Ambiente

Copie o arquivo de exemplo e configure suas variáveis:

```bash
# Linux/macOS
cp env.example .env

# Windows
copy env.example .env
```

Edite o arquivo `.env` com suas configurações:

```bash
# Configurações do Banco de Dados
DATASOURCE_URL=jdbc:postgresql://localhost:5432/postgres
DATASOURCE_USERNAME=postgres
DATASOURCE_PASSWORD=postgres
```

#### 4. Executando a Aplicação

##### Opção 1: Usando Maven Wrapper

```bash
./mvnw spring-boot:run
```

##### Opção 2: Usando Maven

```bash
mvn clean install
mvn spring-boot:run
```

##### Opção 3: Executando o JAR

```bash
mvn clean package
java -jar target/javaweb-0.0.1-SNAPSHOT.jar
```

A aplicação estará disponível em: `http://localhost:8050`

## 📚 Documentação da API

### Endpoints Disponíveis

#### 1. Buscar Créditos por NFS-e

```http
GET /api/creditos/{numeroNfse}
```

**Parâmetros:**

- `numeroNfse` (path): Número da Nota Fiscal de Serviço Eletrônica

**Resposta de Sucesso (200):**

```json
[
  {
    "numeroCredito": "123456",
    "numeroNfse": "7891011",
    "dataConstituicao": "2024-02-25",
    "valorIssqn": 1500.75,
    "tipoCredito": "ISSQN",
    "simplesNacional": true,
    "aliquota": 5.0,
    "valorFaturado": 30000.0,
    "valorDeducao": 5000.0,
    "baseCalculo": 25000.0
  }
]
```

#### 2. Buscar Crédito por Número

```http
GET /api/creditos/credito/{numeroCredito}
```

**Parâmetros:**

- `numeroCredito` (path): Número único do crédito

**Resposta de Sucesso (200):**

```json
{
  "numeroCredito": "123456",
  "numeroNfse": "7891011",
  "dataConstituicao": "2024-02-25",
  "valorIssqn": 1500.75,
  "tipoCredito": "ISSQN",
  "simplesNacional": true,
  "aliquota": 5.0,
  "valorFaturado": 30000.0,
  "valorDeducao": 5000.0,
  "baseCalculo": 25000.0
}
```

#### 3. Health Check

```http
GET /api/creditos/health
```

**Resposta de Sucesso (200):**

```
API de Créditos funcionando!
```

### Códigos de Erro

- **400 Bad Request**: Parâmetros inválidos ou em branco
- **404 Not Found**: Crédito ou NFS-e não encontrado
- **500 Internal Server Error**: Erro interno do servidor

## 🗄️ Estrutura do Banco de Dados

### Tabela: `credito`

| Campo               | Tipo          | Descrição                        |
| ------------------- | ------------- | -------------------------------- |
| `id`                | BIGINT        | Chave primária (auto-incremento) |
| `numero_credito`    | VARCHAR(50)   | Número único do crédito          |
| `numero_nfse`       | VARCHAR(50)   | Número da NFS-e                  |
| `data_constituicao` | DATE          | Data de constituição do crédito  |
| `valor_issqn`       | DECIMAL(15,2) | Valor do ISSQN                   |
| `tipo_credito`      | VARCHAR(50)   | Tipo do crédito (ISSQN, Outros)  |
| `simples_nacional`  | BOOLEAN       | Indica se é Simples Nacional     |
| `aliquota`          | DECIMAL(5,2)  | Alíquota aplicada                |
| `valor_faturado`    | DECIMAL(15,2) | Valor total faturado             |
| `valor_deducao`     | DECIMAL(15,2) | Valor das deduções               |
| `base_calculo`      | DECIMAL(15,2) | Base de cálculo                  |

## 🧪 Testes

### Executando os Testes

```bash
# Executar todos os testes
mvn test

# Executar testes com relatório de cobertura
mvn clean test jacoco:report
```

## 🔧 Desenvolvimento

### Comandos Úteis

```bash
# Compilar o projeto
mvn clean compile

# Executar em modo desenvolvimento com hot reload
mvn spring-boot:run

# Gerar JAR executável
mvn clean package

# Executar migrações do banco
mvn flyway:migrate

# Limpar e resetar banco de dados
mvn flyway:clean flyway:migrate
```

### Configurações de Desenvolvimento

A aplicação está configurada com:

- **Hot Reload**: Ativado via Spring Boot DevTools
- **SQL Logging**: Ativado para debug
- **Flyway**: Migrações automáticas habilitadas

## 📦 Deploy

### Docker (Opcional)

```dockerfile
FROM openjdk:17-jdk-slim
COPY target/javaweb-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
```

### Variáveis de Ambiente para Produção

```bash
# Configurações de Produção
SPRING_PROFILES_ACTIVE=prod
DATASOURCE_URL=jdbc:postgresql://prod-db:5432/creditos
DATASOURCE_USERNAME=prod_user
DATASOURCE_PASSWORD=prod_password
```

## 🚀 Primeira Publicação no Git

Para fazer a primeira publicação no Git, consulte o arquivo [GIT_SETUP.md](GIT_SETUP.md) que contém instruções detalhadas sobre:

- Configuração inicial do Git
- Preparação para o primeiro commit
- Configuração do repositório remoto
- Primeira publicação
- Convenções de commit
- Boas práticas
- Solução de problemas comuns

## 🙏 Agradecimentos

- Spring Boot Team
- PostgreSQL Community
- Flyway Team

## 📞 Suporte

Para suporte, envie um email para suporte@empresa.com ou abra uma issue no repositório.

## 🐳 Subindo o ambiente com Docker Compose

1. **Build e start dos serviços:**

```bash
docker-compose up --build
```

2. **Serviços disponíveis:**

   - API: http://localhost:8080
   - Redpanda (Kafka): localhost:9092
   - Redpanda Admin: http://localhost:9644
   - PostgreSQL: localhost:5432 (user: postgres, senha: postgres)

3. **Parar os serviços:**

```bash
docker-compose down
```

4. **Volumes persistentes:**
   - Dados do Redpanda e PostgreSQL são mantidos em volumes Docker.

---

**Versão:** 0.0.1-SNAPSHOT  
**Última atualização:** Dezembro 2024
