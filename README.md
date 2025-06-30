# 🏦 Sistema de Créditos Fiscais

Sistema completo para gestão de créditos fiscais com API Spring Boot e frontend Angular.

## 📋 Sobre o Projeto

### Backend (API)

- **Tecnologia**: Java 17 + Spring Boot 3.3.12
- **Banco**: PostgreSQL 16
- **Autenticação**: JWT
- **Eventos**: Kafka
- **Porta**: 8050

### Frontend

- **Tecnologia**: Angular 17 + TypeScript
- **Estilização**: Tailwind CSS
- **Porta**: 8080 (Docker) / 4200 (Local)

## 🚀 Instalação com Docker

### Pré-requisitos

- Docker
- Docker Compose

### Executar

```bash
# Clone o repositório
git clone https://github.com/Fredalessandro/desafio-tecnico-alessandro.git
cd desafio-tecnico-alessandro

# Subir todos os serviços
docker-compose up -d

# Verificar status
docker-compose ps
```

### URLs de Acesso

| Serviço           | URL                                       | Descrição     |
| ----------------- | ----------------------------------------- | ------------- |
| **Frontend**      | http://localhost:8080                     | Interface web |
| **API**           | http://localhost:8050                     | API REST      |
| **Swagger**       | http://localhost:8050/api/swagger-ui.html | Documentação  |
| **Kafka Console** | http://localhost:8081                     | Console Kafka |

## 🔧 Comandos Úteis

```bash
# Parar serviços
docker-compose down

# Ver logs
docker-compose logs -f api
docker-compose logs -f frontend

# Rebuild
docker-compose build
docker-compose up -d
```

## 📁 Estrutura

```
├── api-creditos-fiscais/     # Backend Spring Boot
├── front-creditos-fiscais/   # Frontend Angular
└── docker-compose.yml        # Orquestração
```

---
