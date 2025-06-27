# desafio-tecnico

## 📦 Visão Geral

Este projeto orquestra uma API Java Spring Boot, um frontend Angular, PostgreSQL, Kafka e Redpanda Console usando Docker Compose. Compatível com Windows, Linux e macOS (incluindo Apple Silicon/M1/M2).

**Importante:** Este repositório contém submódulos Git que referenciam outros repositórios separados:

- `api-creditos-fiscais/` - Submódulo do repositório da API Java Spring Boot
- `front-creditos-fiscais/` - Submódulo do repositório do frontend Angular

## 🔗 Repositórios dos Submódulos

Este projeto utiliza submódulos Git para organizar os diferentes componentes. Cada submódulo tem seu próprio repositório:

### Backend (API)

- **Repositório:** [api-creditos-fiscais](https://github.com/Fredalessandro/api-creditos-fiscais)
- **Tecnologia:** Java 17 + Spring Boot
- **Diretório local:** `api-creditos-fiscais/`

### Frontend

- **Repositório:** [front-creditos-fiscais](https://github.com/Fredalessandro/front-creditos-fiscais)
- **Tecnologia:** Angular + Node.js
- **Diretório local:** `front-creditos-fiscais/`

> **💡 Dica:** Para contribuir com melhorias específicas de cada componente, acesse diretamente os repositórios individuais dos submódulos.

---

## 🐳 Subindo o ambiente com Docker Compose

### Pré-requisitos

- [Docker Desktop](https://www.docker.com/products/docker-desktop) (Windows/macOS)
- [Docker Engine + Docker Compose](https://docs.docker.com/compose/install/) (Linux)
- Git

> **Atenção Mac M1/M2:**
> O arquivo força a arquitetura `linux/amd64` para máxima compatibilidade. Caso note lentidão, ative a emulação (Rosetta/Colima) ou ajuste/remova a linha `platform: linux/amd64` nos serviços do docker-compose.

### Clonando o repositório

**⚠️ IMPORTANTE:** Como este projeto contém submódulos Git, você deve usar o comando com `--recurse-submodules` para baixar todos os submódulos automaticamente:

```sh
git clone --recurse-submodules https://github.com/Fredalessandro/desafio-tecnico-alessandro.git
cd desafio-tecnico-alessandro
```

**Alternativa:** Se você já clonou sem submódulos, pode baixá-los depois com:

```sh
git submodule update --init --recursive
```

### Subindo os containers

```sh
docker-compose up --build
```

### Parando os containers

```sh
docker-compose down
```

---

## 🔎 Serviços, Imagens e Portas

| Serviço              | Imagem/Base                              | Porta Host:Container | Descrição                       |
| -------------------- | ---------------------------------------- | -------------------- | ------------------------------- |
| **API**              | Builda local (Java 17, Spring Boot)      | 8050:8050            | Backend REST principal          |
| **Frontend**         | Builda local (Node 20 + Nginx)           | 8080:80              | SPA Angular                     |
| **PostgreSQL**       | postgres:16                              | 5432:5432            | Banco de dados                  |
| **Kafka**            | obsidiandynamics/kafka                   | 9092:9092, 2181:2181 | Broker de eventos               |
| **Redpanda Console** | docker.redpanda.com/redpandadata/console | 8081:8080            | UI para monitorar tópicos Kafka |

### Credenciais Padrão

- **PostgreSQL**: usuário `postgres`, senha `postgres`
- **Kafka**: sem autenticação

---

## 🌐 Como acessar

- **Frontend:** [http://localhost:8080](http://localhost:8080)
- **API:** [http://localhost:8050/api/creditos/health](http://localhost:8050/api/creditos/health)
- **Redpanda Console:** [http://localhost:8081](http://localhost:8081)
- **Kafka Broker:** `localhost:9092`
- **PostgreSQL:** `localhost:5432` (pode conectar via DBeaver, TablePlus, etc)

---

## ⚙️ Executando localmente (sem Docker)

### Backend (API)

1. Instale Java 17+ e Maven 3.6+
2. Configure o banco PostgreSQL localmente (usuário/senha padrão: postgres)
3. Copie `env.example` para `.env` e ajuste se necessário
4. Execute:
   ```sh
   ./mvnw spring-boot:run
   # ou
   mvn spring-boot:run
   ```
5. API disponível em [http://localhost:8050](http://localhost:8050)

### Frontend

1. Instale Node.js 20+
2. No diretório `front-creditos-fiscais`:
   ```sh
   npm install --legacy-peer-deps
   npm start
   # ou
   ng serve
   ```
3. Frontend disponível em [http://localhost:4200](http://localhost:4200)

---

## 📝 Dicas de Compatibilidade

- **Windows:** Use Docker Desktop e Git Bash/PowerShell para comandos.
- **Linux:** Docker Compose pode exigir `sudo`.
- **macOS M1/M2:** Se notar lentidão, ative Rosetta/Colima ou remova `platform: linux/amd64` dos serviços.

---

## 🛠️ Informações do Git

- Clone: `git clone --recurse-submodules https://github.com/Fredalessandro/desafio-tecnico-alessandro.git`
- **Submódulos:** Este projeto contém submódulos que referenciam repositórios separados para API e Frontend
- Recomenda-se criar branches para novas features/fixes.
- Faça commits frequentes e mensagens claras.
- Para contribuir, abra um Pull Request.

---

## 📚 Referências

- [Documentação oficial Docker Compose](https://docs.docker.com/compose/)
- [Documentação Spring Boot](https://spring.io/projects/spring-boot)
- [Documentação Angular](https://angular.io/)

---

**Dúvidas?** Abra uma issue ou envie um e-mail para o responsável pelo repositório.
