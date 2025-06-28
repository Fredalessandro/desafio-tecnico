# 🚀 API de Créditos Fiscais

API REST para gestão de créditos fiscais desenvolvida em Spring Boot com autenticação JWT e auditoria via Kafka.

## 📋 Índice

- [Visão Geral](#visão-geral)
- [Tecnologias](#tecnologias)
- [Estrutura](#estrutura)
- [Instalação](#instalação)
- [Configuração](#configuração)
- [API](#api)
- [Testes](#testes)
- [Deploy](#deploy)

## 🎯 Visão Geral

API desenvolvida para gerenciar créditos fiscais, permitindo consultas por NFS-e e número do crédito. Inclui autenticação JWT, auditoria via Kafka e documentação OpenAPI.

### Funcionalidades

- ✅ Consulta de créditos por NFS-e
- ✅ Consulta de crédito por número
- ✅ Autenticação JWT
- ✅ Cadastro e gestão de usuários
- ✅ Auditoria via Kafka
- ✅ Documentação OpenAPI/Swagger
- ✅ Migrações automáticas com Flyway
- ✅ Health checks

## 🛠 Tecnologias

- **Java 17**
- **Spring Boot 3.3.12**
- **Spring Security + JWT**
- **Spring Data JPA**
- **PostgreSQL 16**
- **Flyway** (Migrações)
- **Kafka** (Eventos)
- **MapStruct** (Mapeamento)
- **Lombok**
- **Maven**
- **SpringDoc OpenAPI**

## 📁 Estrutura

```
src/main/java/com/desafio/credito/
├── config/                 # Configurações
│   ├── JwtUtil.java       # Utilitário JWT
│   ├── SecurityConfig.java # Configuração de segurança
│   ├── OpenApiConfig.java # Configuração OpenAPI
│   └── KafkaTopicConfig.java # Configuração Kafka
├── controller/            # Controllers REST
│   ├── CreditoController.java
│   └── UsuarioController.java
├── dto/                  # Data Transfer Objects
│   ├── CreditoDTO.java
│   ├── UsuarioDTO.java
│   ├── LoginRequestDTO.java
│   └── LoginResponseDTO.java
├── entity/               # Entidades JPA
│   ├── Credito.java
│   └── Usuario.java
├── mapper/               # Mapeamentos MapStruct
│   ├── CreditoMapper.java
│   └── UsuarioMapper.java
├── repository/           # Repositórios JPA
│   ├── CreditoRepository.java
│   └── UsuarioRepository.java
├── service/              # Lógica de negócio
│   ├── CreditoService.java
│   ├── UsuarioService.java
│   └── CreditoEventPublisher.java
├── exception/            # Tratamento de exceções
│   ├── GlobalExceptionHandler.java
│   ├── ResourceNotFoundException.java
│   └── ErrorResponse.java
└── event/                # Eventos Kafka
    ├── CreditoConsultaEvent.java
    ├── EnumStatusConsulta.java
    └── EnunTipoConsulta.java
```

## 🚀 Instalação

### Pré-requisitos

- Java 17+
- Maven 3.8+
- PostgreSQL 16+
- Kafka (opcional para desenvolvimento local)

### Execução Local

1. **Clone o repositório**

   ```bash
   git clone <repository-url>
   cd api-creditos-fiscais
   ```

2. **Configure o banco de dados**

   ```bash
   # Copie o arquivo de exemplo
   cp env.example .env

   # Edite as variáveis de ambiente
   nano .env
   ```

3. **Execute a aplicação**

   ```bash
   # Com Maven Wrapper
   ./mvnw spring-boot:run

   # Ou com Maven
   mvn spring-boot:run
   ```

### Docker

```bash
# Build da imagem
docker build -t api-creditos-fiscais .

# Execução
docker run -p 8050:8050 api-creditos-fiscais
```

## ⚙️ Configuração

### Variáveis de Ambiente

| Variável                         | Descrição          | Padrão                                      |
| -------------------------------- | ------------------ | ------------------------------------------- |
| `SPRING_PROFILES_ACTIVE`         | Perfil ativo       | `dev`                                       |
| `SERVER_PORT`                    | Porta da aplicação | `8050`                                      |
| `SPRING_DATASOURCE_URL`          | URL do banco       | `jdbc:postgresql://localhost:5432/postgres` |
| `SPRING_DATASOURCE_USERNAME`     | Usuário do banco   | `postgres`                                  |
| `SPRING_DATASOURCE_PASSWORD`     | Senha do banco     | `postgres`                                  |
| `SPRING_KAFKA_BOOTSTRAP_SERVERS` | Servidores Kafka   | `localhost:9092`                            |

### Perfis

- **dev**: Desenvolvimento local
- **test**: Testes
- **prod**: Produção

## 🔌 API

### Base URL

```
http://localhost:8050/api
```

### Endpoints

#### Autenticação

```http
POST /usuarios/login
Content-Type: application/json

{
  "login": "usuario",
  "senha": "senha123"
}
```

#### Usuários

```http
POST /usuarios                    # Cadastrar usuário
GET /usuarios                     # Listar usuários
GET /usuarios/{id}                # Buscar usuário por ID
DELETE /usuarios/{id}             # Deletar usuário
```

#### Créditos

```http
GET /creditos/{numeroNfse}        # Buscar créditos por NFS-e
GET /creditos/credito/{numeroCredito} # Buscar crédito por número
GET /creditos/health              # Health check
GET /creditos/status              # Status da API
```

### Documentação

- **Swagger UI**: http://localhost:8050/api/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8050/api/v3/api-docs

## 🧪 Testes

### Executar Testes

```bash
# Todos os testes
./mvnw test

# Testes específicos
./mvnw test -Dtest=CreditoControllerTest

# Testes de integração
./mvnw test -Dtest=*IT
```

### Cobertura

```bash
# Gerar relatório de cobertura
./mvnw jacoco:report
```

## 🐳 Deploy

### Docker Compose

```yaml
api:
  build: .
  ports:
    - "8050:8050"
  environment:
    SPRING_PROFILES_ACTIVE: prod
    SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/postgres
  depends_on:
    postgres:
      condition: service_healthy
```

### Kubernetes

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: api-creditos-fiscais
spec:
  replicas: 3
  selector:
    matchLabels:
      app: api-creditos-fiscais
  template:
    metadata:
      labels:
        app: api-creditos-fiscais
    spec:
      containers:
        - name: api
          image: api-creditos-fiscais:latest
          ports:
            - containerPort: 8050
          env:
            - name: SPRING_PROFILES_ACTIVE
              value: "prod"
```

## 🔧 Comandos Úteis

### Desenvolvimento

```bash
# Compilar
./mvnw compile

# Executar com hot reload
./mvnw spring-boot:run

# Gerar JAR
./mvnw clean package

# Limpar
./mvnw clean
```

### Banco de Dados

```bash
# Executar migrações
./mvnw flyway:migrate

# Limpar banco
./mvnw flyway:clean

# Validar migrações
./mvnw flyway:validate
```

## 🐛 Troubleshooting

### Problemas Comuns

1. **Erro de conexão com banco**

   ```bash
   # Verifique se o PostgreSQL está rodando
   pg_isready -h localhost -p 5432

   # Verifique as credenciais
   psql -h localhost -U postgres -d postgres
   ```

2. **Erro de migração Flyway**

   ```bash
   # Verifique o histórico
   ./mvnw flyway:info

   # Repare migrações
   ./mvnw flyway:repair
   ```

3. **Erro de compilação**

   ```bash
   # Limpe e recompile
   ./mvnw clean compile

   # Verifique versão do Java
   java -version
   ```

## 📊 Monitoramento

### Health Checks

- **Health**: `/api/creditos/health`
- **Status**: `/api/creditos/status`

### Métricas

- **Actuator**: `/actuator` (se configurado)
- **Prometheus**: `/actuator/prometheus` (se configurado)

## 🤝 Contribuição

1. Fork o projeto
2. Crie uma branch (`git checkout -b feature/nova-funcionalidade`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova funcionalidade'`)
4. Push para a branch (`git push origin feature/nova-funcionalidade`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT.

---

**Desenvolvido com ❤️ por [Seu Nome]**
