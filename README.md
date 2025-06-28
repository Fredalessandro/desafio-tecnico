# 🏦 Sistema de Créditos Fiscais

Sistema completo para gestão de créditos fiscais com API REST em Spring Boot e frontend em Angular.

## 📋 Índice

- [Visão Geral](#visão-geral)
- [Tecnologias](#tecnologias)
- [Arquitetura](#arquitetura)
- [Pré-requisitos](#pré-requisitos)
- [Instalação](#instalação)
- [Uso](#uso)
- [API](#api)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Contribuição](#contribuição)
- [Licença](#licença)

## 🎯 Visão Geral

Sistema desenvolvido para gerenciar créditos fiscais, permitindo consultas por NFS-e e número do crédito. Inclui autenticação JWT, auditoria via Kafka e interface web responsiva.

### Funcionalidades

- ✅ Consulta de créditos por NFS-e
- ✅ Consulta de crédito por número
- ✅ Autenticação JWT
- ✅ Cadastro de usuários
- ✅ Auditoria via Kafka
- ✅ Interface web responsiva
- ✅ Documentação OpenAPI/Swagger

## 🛠 Tecnologias

### Backend

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

### Frontend

- **Angular 17**
- **TypeScript**
- **Tailwind CSS**
- **RxJS**

### Infraestrutura

- **Docker & Docker Compose**
- **PostgreSQL**
- **Kafka**
- **Redpanda Console**

## 🏗 Arquitetura

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Frontend      │    │   API REST      │    │   PostgreSQL    │
│   (Angular)     │◄──►│   (Spring Boot) │◄──►│   (Database)    │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                              │
                              ▼
                       ┌─────────────────┐
                       │   Kafka         │
                       │   (Events)      │
                       └─────────────────┘
```

## 📋 Pré-requisitos

- **Docker** (versão 20.10+)
- **Docker Compose** (versão 2.0+)
- **Java 17** (para desenvolvimento local)
- **Node.js 18+** (para desenvolvimento local)
- **Maven 3.8+** (para desenvolvimento local)

## 🚀 Instalação

### Opção 1: Docker (Recomendado)

1. **Clone o repositório**

   ```bash
   git clone <repository-url>
   cd desafio-tecnico-alessandro
   ```

2. **Execute com Docker Compose**

   ```bash
   docker-compose up -d
   ```

3. **Aguarde a inicialização**

   ```bash
   # Verifique o status dos containers
   docker-compose ps

   # Acompanhe os logs
   docker-compose logs -f api
   ```

### Opção 2: Desenvolvimento Local

1. **Backend**

   ```bash
   cd api-creditos-fiscais
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```

2. **Frontend**
   ```bash
   cd front-creditos-fiscais
   npm install
   npm start
   ```

## 🌐 Uso

### URLs de Acesso

| Serviço              | URL                                       | Descrição        |
| -------------------- | ----------------------------------------- | ---------------- |
| **Frontend**         | http://localhost:8080                     | Interface web    |
| **API**              | http://localhost:8050                     | API REST         |
| **Swagger**          | http://localhost:8050/api/swagger-ui.html | Documentação API |
| **Redpanda Console** | http://localhost:8081                     | Console Kafka    |
| **PostgreSQL**       | localhost:5432                            | Banco de dados   |

### Primeiro Acesso

1. Acesse http://localhost:8080
2. Faça o cadastro de um usuário
3. Faça login com as credenciais
4. Use o sistema para consultar créditos

## 🔌 API

### Endpoints Principais

#### Autenticação

```http
POST /api/usuarios/login
Content-Type: application/json

{
  "login": "usuario",
  "senha": "senha123"
}
```

#### Consulta de Créditos

```http
GET /api/creditos/{numeroNfse}
Authorization: Bearer <token>

GET /api/creditos/credito/{numeroCredito}
Authorization: Bearer <token>
```

#### Health Check

```http
GET /api/creditos/health
GET /api/creditos/status
```

### Documentação Completa

Acesse http://localhost:8050/api/swagger-ui.html para documentação interativa.

## 📁 Estrutura do Projeto

```
desafio-tecnico-alessandro/
├── api-creditos-fiscais/          # Backend Spring Boot
│   ├── src/main/java/
│   │   └── com/desafio/credito/
│   │       ├── config/            # Configurações
│   │       ├── controller/        # Controllers REST
│   │       ├── dto/              # Data Transfer Objects
│   │       ├── entity/           # Entidades JPA
│   │       ├── mapper/           # Mapeamentos MapStruct
│   │       ├── repository/       # Repositórios JPA
│   │       ├── service/          # Lógica de negócio
│   │       └── exception/        # Tratamento de exceções
│   └── src/main/resources/
│       └── db/migration/         # Migrações Flyway
├── front-creditos-fiscais/        # Frontend Angular
│   ├── src/app/
│   │   ├── auth/                 # Autenticação
│   │   ├── credito/              # Módulo de créditos
│   │   ├── guards/               # Guards de rota
│   │   ├── interceptors/         # Interceptors HTTP
│   │   ├── models/               # Modelos TypeScript
│   │   └── services/             # Serviços Angular
│   └── src/environments/         # Configurações de ambiente
└── docker-compose.yml            # Orquestração Docker
```

## 🧪 Testes

### Backend

```bash
cd api-creditos-fiscais
./mvnw test
```

### Frontend

```bash
cd front-creditos-fiscais
npm test
```

## 🔧 Comandos Úteis

### Docker

```bash
# Iniciar todos os serviços
docker-compose up -d

# Parar todos os serviços
docker-compose down

# Ver logs
docker-compose logs -f api

# Rebuild da API
docker-compose build api

# Acessar container
docker-compose exec api sh
```

### Desenvolvimento

```bash
# Testar conexão
./test-connection.sh

# Limpar volumes (cuidado!)
docker-compose down -v

# Verificar saúde dos serviços
docker-compose ps
```

## 🐛 Troubleshooting

### Problemas Comuns

1. **API não conecta ao PostgreSQL**

   ```bash
   # Verifique se o PostgreSQL está saudável
   docker-compose ps postgres

   # Verifique logs da API
   docker-compose logs api
   ```

2. **Erro de migração Flyway**

   ```bash
   # Rebuild da API
   docker-compose build api
   docker-compose up -d api
   ```

3. **Frontend não carrega**
   ```bash
   # Verifique se a API está rodando
   curl http://localhost:8050/api/creditos/health
   ```

## 🤝 Contribuição

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## 📞 Suporte

Para dúvidas ou problemas:

- Abra uma [Issue](../../issues)
- Entre em contato: [seu-email@exemplo.com]

---

**Desenvolvido com ❤️ por [Seu Nome]**
