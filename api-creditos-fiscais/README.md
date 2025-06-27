# API de Créditos Fiscais

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.java.net/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.12-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.11.0-blue.svg)](https://maven.apache.org/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-13+-blue.svg)](https://www.postgresql.org/)
[![Flyway](https://img.shields.io/badge/Flyway-9.22.3-yellow.svg)](https://flywaydb.org/)
[![Docker](https://img.shields.io/badge/Docker-Ready-blue.svg)](https://www.docker.com/)

## 📋 Descrição

API REST desenvolvida em Spring Boot para consulta de créditos fiscais. O sistema permite consultar créditos constituídos associados a Notas Fiscais de Serviço Eletrônica (NFS-e) e fornece funcionalidades para busca e validação de dados fiscais, além de autenticação JWT e gerenciamento de usuários.

## 🚀 Tecnologias Utilizadas

- **Java 17** - Linguagem de programação
- **Spring Boot 3.3.12** - Framework para desenvolvimento de aplicações Java
- **Spring Data JPA** - Persistência de dados
- **Spring Security** - Segurança e autenticação
- **PostgreSQL** - Banco de dados relacional
- **Flyway** - Migração de banco de dados
- **MapStruct** - Mapeamento entre objetos
- **Lombok** - Redução de código boilerplate
- **Maven** - Gerenciamento de dependências
- **Kafka** - Mensageria e eventos
- **JWT** - Autenticação baseada em tokens
- **OpenAPI/Swagger** - Documentação da API

## 🌐 Informações de Acesso

### Portas e URLs

| Serviço           | Porta | URL                                       | Descrição               |
| ----------------- | ----- | ----------------------------------------- | ----------------------- |
| **API Principal** | 8050  | http://localhost:8050                     | API REST principal      |
| **Health Check**  | 8050  | http://localhost:8050/api/creditos/status | Status da aplicação     |
| **Swagger UI**    | 8050  | http://localhost:8050/swagger-ui.html     | Documentação interativa |
| **PostgreSQL**    | 5432  | localhost:5432                            | Banco de dados          |
| **Kafka**         | 9092  | localhost:9092                            | Broker de mensagens     |

### Credenciais Padrão

- **PostgreSQL**: `postgres` / `postgres`
- **API**: Sem autenticação para endpoints públicos
- **JWT**: Necessário para endpoints protegidos

## 📁 Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/desafio/credito/
│   │   ├── controller/          # Controladores REST
│   │   │   ├── CreditoController.java
│   │   │   └── UsuarioController.java
│   │   ├── service/             # Lógica de negócio
│   │   │   ├── CreditoService.java
│   │   │   ├── UsuarioService.java
│   │   │   └── CreditoEventPublisher.java
│   │   ├── repository/          # Acesso a dados
│   │   │   ├── CreditoRepository.java
│   │   │   └── UsuarioRepository.java
│   │   ├── entity/              # Entidades JPA
│   │   │   ├── Credito.java
│   │   │   └── Usuario.java
│   │   ├── dto/                 # Objetos de transferência
│   │   │   ├── CreditoDTO.java
│   │   │   ├── UsuarioDTO.java
│   │   │   ├── LoginRequestDTO.java
│   │   │   └── LoginResponseDTO.java
│   │   ├── mapper/              # Mapeadores MapStruct
│   │   │   ├── CreditoMapper.java
│   │   │   └── UsuarioMapper.java
│   │   ├── config/              # Configurações
│   │   │   ├── SecurityConfig.java
│   │   │   ├── JwtUtil.java
│   │   │   ├── OpenApiConfig.java
│   │   │   └── KafkaTopicConfig.java
│   │   ├── exception/           # Tratamento de exceções
│   │   │   ├── GlobalExceptionHandler.java
│   │   │   ├── ErrorResponse.java
│   │   │   └── ResourceNotFoundException.java
│   │   ├── event/               # Eventos Kafka
│   │   │   ├── CreditoConsultaEvent.java
│   │   │   ├── EnumStatusConsulta.java
│   │   │   └── EnunTipoConsulta.java
│   │   └── JavawebApplication.java
│   └── resources/
│       ├── application.yml      # Configurações da aplicação
│       └── db/migration/        # Scripts de migração Flyway
├── README.md                    # Documentação principal
├── GIT_SETUP.md                 # Guia de primeira publicação no Git
├── setup.sh                     # Script de setup (Linux/macOS)
├── setup.bat                    # Script de setup (Windows)
├── env.example                  # Exemplo de variáveis de ambiente
├── Dockerfile                   # Configuração Docker
└── pom.xml                      # Configuração do Maven
```

## 🛠️ Pré-requisitos

- **Java 17** ou superior
- **Maven 3.6+**
- **PostgreSQL 13+**
- **Git**
- **Docker** (opcional, para execução em container)

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
cd api-creditos-fiscais
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

# Porta da aplicação
SERVER_PORT=8050

# Configurações de Log
LOGGING_LEVEL_ROOT=INFO
LOGGING_LEVEL_COM_DESAFIO=DEBUG
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

#### 🔐 Autenticação

##### Login

```http
POST /usuarios/login
Content-Type: application/json

{
  "login": "joaosilva",
  "senha": "123456"
}
```

**Resposta de Sucesso (200):**

```json
{
  "nome": "João da Silva",
  "login": "joaosilva",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6..."
}
```

#### 👥 Usuários

##### Cadastrar Usuário

```http
POST /usuarios
Content-Type: application/json

{
  "nome": "João da Silva",
  "login": "joaosilva",
  "senha": "123456"
}
```

##### Listar Usuários

```http
GET /usuarios
Authorization: Bearer <token>
```

##### Buscar Usuário por ID

```http
GET /usuarios/{id}
Authorization: Bearer <token>
```

##### Deletar Usuário

```http
DELETE /usuarios/{id}
Authorization: Bearer <token>
```

#### 💰 Créditos Fiscais

##### Buscar Créditos por NFS-e

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

##### Buscar Crédito por Número

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

##### Health Check

```http
GET /api/creditos/status
```

**Resposta de Sucesso (200):**

```
API de Créditos funcionando!
```

### Códigos de Erro

- **400 Bad Request**: Parâmetros inválidos ou em branco
- **401 Unauthorized**: Token JWT inválido ou ausente
- **404 Not Found**: Crédito, NFS-e ou usuário não encontrado
- **409 Conflict**: Login já está em uso
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

### Tabela: `usuario`

| Campo   | Tipo         | Descrição                        |
| ------- | ------------ | -------------------------------- |
| `id`    | BIGINT       | Chave primária (auto-incremento) |
| `nome`  | VARCHAR(100) | Nome completo do usuário         |
| `login` | VARCHAR(50)  | Login único do usuário           |
| `senha` | VARCHAR(255) | Senha criptografada              |

## 🧪 Testes

### Executando os Testes

```bash
# Executar todos os testes
mvn test

# Executar testes com relatório de cobertura
mvn clean test jacoco:report

# Executar testes de integração
mvn test -Dtest=*IT
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

# Gerar documentação OpenAPI
mvn spring-boot:run
# Acesse: http://localhost:8050/swagger-ui.html
```

### Configurações de Desenvolvimento

A aplicação está configurada com:

- **Hot Reload**: Ativado via Spring Boot DevTools
- **SQL Logging**: Ativado para debug
- **Flyway**: Migrações automáticas habilitadas
- **Swagger UI**: Documentação interativa disponível

## 🐳 Docker

### Build da Imagem

```bash
docker build -t api-creditos-fiscais .
```

### Executar Container

```bash
docker run -p 8050:8050 \
  -e DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/postgres \
  -e DATASOURCE_USERNAME=postgres \
  -e DATASOURCE_PASSWORD=postgres \
  api-creditos-fiscais
```

### Docker Compose

```yaml
version: "3.8"
services:
  api:
    build: .
    ports:
      - "8050:8050"
    environment:
      - DATASOURCE_URL=jdbc:postgresql://postgres:5432/postgres
      - DATASOURCE_USERNAME=postgres
      - DATASOURCE_PASSWORD=postgres
    depends_on:
      - postgres
      - kafka

  postgres:
    image: postgres:16
    environment:
      POSTGRES_DB: postgres
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

  kafka:
    image: obsidiandynamics/kafka
    ports:
      - "9092:9092"
      - "2181:2181"
    environment:
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
      KAFKA_ZOOKEEPER_CONNECT: localhost:2181

volumes:
  postgres_data:
```

## 📦 Deploy

### Variáveis de Ambiente para Produção

```bash
# Configurações de Produção
SPRING_PROFILES_ACTIVE=prod
SERVER_PORT=8050
DATASOURCE_URL=jdbc:postgresql://prod-db:5432/creditos
DATASOURCE_USERNAME=prod_user
DATASOURCE_PASSWORD=prod_password
LOGGING_LEVEL_ROOT=WARN
SPRING_JPA_SHOW_SQL=false
```

### Build para Produção

```bash
mvn clean package -Pprod
java -jar target/javaweb-0.0.1-SNAPSHOT.jar
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

## 🔍 Monitoramento e Logs

### Health Check

```bash
curl http://localhost:8050/api/creditos/status
```

### Logs da Aplicação

```bash
# Ver logs em tempo real
tail -f logs/application.log

# Filtrar logs de erro
grep "ERROR" logs/application.log
```

### Métricas (se configurado)

```bash
curl http://localhost:8050/actuator/health
curl http://localhost:8050/actuator/metrics
```

## 🙏 Agradecimentos

- Spring Boot Team
- PostgreSQL Community
- Flyway Team
- Apache Kafka Community

## 📞 Suporte

Para suporte, envie um email para suporte@empresa.com ou abra uma issue no repositório.

---

**Versão:** 0.0.1-SNAPSHOT  
**Última atualização:** Dezembro 2024  
**Porta padrão:** 8050  
**Java:** 17  
**Spring Boot:** 3.3.12
