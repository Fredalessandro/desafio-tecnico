# 🎨 Frontend - Sistema de Créditos Fiscais

Interface web para gestão de créditos fiscais desenvolvida em Angular.

## 🛠 Tecnologias

- **Angular 17**
- **TypeScript**
- **Tailwind CSS**
- **RxJS**
- **Angular Router**

## 🚀 Execução

### Local

```bash
# Instalar dependências
npm install

# Executar em desenvolvimento
npm start
```

### Docker

```bash
# Build
docker build -t frontend-creditos-fiscais .

# Executar
docker run -p 8080:80 frontend-creditos-fiscais
```

## 🌐 Portas e URLs

| Serviço               | Porta | URL                   |
| --------------------- | ----- | --------------------- |
| **Frontend**          | 4200  | http://localhost:4200 |
| **Frontend (Docker)** | 8080  | http://localhost:8080 |

## 📁 Estrutura

```
src/app/
├── auth/              # Autenticação (login/cadastro)
├── credito/           # Módulo de créditos
├── guards/            # Guards de rota
├── interceptors/      # Interceptors HTTP
├── services/          # Serviços
├── models/            # Modelos TypeScript
└── template/          # Template base
```

## ⚙️ Configuração

### Proxy para API

```json
{
  "/api": {
    "target": "http://localhost:8050",
    "secure": false,
    "changeOrigin": true
  }
}
```

### Variáveis de Ambiente

```typescript
export const environment = {
  production: false,
  apiUrl: "http://localhost:8050/api",
};
```

## 🔧 Comandos Úteis

```bash
# Desenvolvimento
npm start

# Build
npm run build

# Testes
npm test

# Build com watch
npm run watch
```

## 🧪 Testes

```bash
npm test
```

---

**Desenvolvido com ❤️**
