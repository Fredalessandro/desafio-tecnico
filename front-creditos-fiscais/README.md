# Consulta de Créditos NFE - Frontend

Este projeto é uma aplicação Angular standalone para consulta de créditos de NFE.

## Pré-requisitos

- [Node.js](https://nodejs.org/) (opcional, apenas para execução local sem Docker)
- [Docker](https://www.docker.com/) e [Docker Compose](https://docs.docker.com/compose/)
- Git

## Clonando o repositório

```sh
git clone https://github.com/Fredalessandro/front-creditos-fiscais.git
cd frontend
```

## Executando com Docker Compose (recomendado)

1. **Build e subida dos containers:**
   ```sh
   docker-compose up --build
   ```
2. Acesse [http://localhost:8080](http://localhost:8080) no navegador.

## Executando localmente (sem Docker)

1. Instale as dependências:
   ```sh
   npm install --legacy-peer-deps
   ```
2. Rode o build de produção:
   ```sh
   npm run build
   ```
3. Sirva a pasta de build (`dist/app`) com algum servidor estático, por exemplo:
   ```sh
   npx serve -s dist/app
   ```
   Ou use o próprio Angular em modo dev:
   ```sh
   npm start
   # ou
   ng serve
   ```

## Estrutura dos arquivos importantes

- `Dockerfile`: Builda e serve a aplicação com Nginx
- `nginx.conf`: Configuração do Nginx para SPA
- `docker-compose.yml`: Orquestra o serviço frontend
- `dist/app`: Pasta de build gerada pelo Angular

---
