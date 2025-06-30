# 🚀 API de Créditos Fiscais

API REST para gestão de créditos fiscais desenvolvida em Spring Boot.

## 🛠 Tecnologias

- **Java 17** + **Spring Boot 3.3.12**
- **PostgreSQL 16** + **Flyway**
- **Spring Security + JWT**
- **Kafka** (Eventos)
- **Maven**

## 🚀 Execução

### Local

```bash
# Instalar dependências
./mvnw clean install

# Executar
./mvnw spring-boot:run
```

### Docker

```bash
# Build
docker build -t api-creditos-fiscais .

# Executar
docker run -p 8050:8050 api-creditos-fiscais
```

## 🌐 Portas e URLs

| Serviço     | Porta | URL                                       |
| ----------- | ----- | ----------------------------------------- |
| **API**     | 8050  | http://localhost:8050                     |
| **Swagger** | 8050  | http://localhost:8050/api/swagger-ui.html |
| **Health**  | 8050  | http://localhost:8050/api/creditos/health |

## 🔌 Endpoints Principais

### Autenticação

```http
POST /api/usuarios/login
POST /api/usuarios
```

### Créditos

```http
GET /api/creditos/{numeroNfse}
GET /api/creditos/credito/{numeroCredito}
GET /api/creditos/health
```

## ⚙️ Configuração

### Variáveis de Ambiente

```bash
SPRING_PROFILES_ACTIVE=dev
SERVER_PORT=8050
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/postgres
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=postgres
```

## 🧪 Testes

```bash
./mvnw test
```

## 📁 Estrutura

```
src/main/java/com/desafio/credito/
├── controller/     # Controllers REST
├── service/        # Lógica de negócio
├── repository/     # Acesso a dados
├── entity/         # Entidades JPA
├── dto/           # DTOs
├── config/        # Configurações
└── exception/     # Tratamento de exceções
```

## 🔧 Comandos Úteis

```bash
# Compilar
./mvnw compile

# Executar com hot reload
./mvnw spring-boot:run

# Gerar JAR
./mvnw clean package

# Migrações
./mvnw flyway:migrate
```

---

**Desenvolvido com ❤️**
