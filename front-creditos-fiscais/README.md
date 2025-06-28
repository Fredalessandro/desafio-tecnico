# 🎨 Frontend - Sistema de Créditos Fiscais

Interface web responsiva para gestão de créditos fiscais desenvolvida em Angular com autenticação JWT e design moderno.

## 📋 Índice

- [Visão Geral](#visão-geral)
- [Tecnologias](#tecnologias)
- [Estrutura](#estrutura)
- [Instalação](#instalação)
- [Configuração](#configuração)
- [Desenvolvimento](#desenvolvimento)
- [Build](#build)
- [Deploy](#deploy)

## 🎯 Visão Geral

Frontend desenvolvido para consumir a API de créditos fiscais, oferecendo uma interface intuitiva para consultas, autenticação e gestão de usuários.

### Funcionalidades

- ✅ Interface responsiva e moderna
- ✅ Autenticação JWT
- ✅ Consulta de créditos por NFS-e
- ✅ Consulta de crédito por número
- ✅ Cadastro e login de usuários
- ✅ Guards de rota
- ✅ Interceptors HTTP
- ✅ Design system com Tailwind CSS

## 🛠 Tecnologias

- **Angular 17**
- **TypeScript**
- **Tailwind CSS**
- **RxJS**
- **Angular Router**
- **Angular Forms**
- **Angular HTTP Client**

## 📁 Estrutura

```
src/app/
├── auth/                    # Autenticação
│   ├── login/              # Componente de login
│   └── cadastro/           # Componente de cadastro
├── credito/                # Módulo de créditos
│   ├── credito/            # Componente principal
│   ├── credito.service.ts  # Serviço de créditos
│   └── credito.routes.ts   # Rotas do módulo
├── guards/                 # Guards de rota
│   ├── auth.guard.ts       # Guard de autenticação
│   └── guest.guard.ts      # Guard para usuários não autenticados
├── interceptors/           # Interceptors HTTP
│   └── auth.interceptor.ts # Interceptor de autenticação
├── models/                 # Modelos TypeScript
│   └── usuario.model.ts    # Modelo de usuário
├── services/               # Serviços
│   └── auth.service.ts     # Serviço de autenticação
└── template/               # Template base
    └── layout/             # Layout principal
```

## 🚀 Instalação

### Pré-requisitos

- Node.js 18+
- npm ou yarn
- Angular CLI (opcional)

### Execução Local

1. **Clone o repositório**

   ```bash
   git clone <repository-url>
   cd front-creditos-fiscais
   ```

2. **Instale as dependências**

   ```bash
   npm install
   ```

3. **Execute em modo desenvolvimento**

   ```bash
   npm start
   ```

4. **Acesse a aplicação**
   ```
   http://localhost:4200
   ```

### Docker

```bash
# Build da imagem
docker build -t frontend-creditos-fiscais .

# Execução
docker run -p 8080:80 frontend-creditos-fiscais
```

## ⚙️ Configuração

### Variáveis de Ambiente

Crie o arquivo `src/environments/environment.ts`:

```typescript
export const environment = {
  production: false,
  apiUrl: "http://localhost:8050/api",
  appName: "Sistema de Créditos Fiscais",
};
```

### Configuração do Proxy

O arquivo `proxy.conf.json` está configurado para redirecionar chamadas da API:

```json
{
  "/api": {
    "target": "http://localhost:8050",
    "secure": false,
    "changeOrigin": true
  }
}
```

## 💻 Desenvolvimento

### Comandos Disponíveis

```bash
# Servidor de desenvolvimento
npm start

# Build para produção
npm run build

# Executar testes
npm test

# Lint do código
npm run lint

# Build com watch
npm run watch
```

### Estrutura de Componentes

#### Auth Module

- **LoginComponent**: Tela de login
- **CadastroComponent**: Tela de cadastro

#### Credito Module

- **CreditoComponent**: Tela principal de consulta

#### Guards

- **AuthGuard**: Protege rotas que requerem autenticação
- **GuestGuard**: Protege rotas para usuários não autenticados

#### Services

- **AuthService**: Gerencia autenticação e tokens JWT
- **CreditoService**: Consome a API de créditos

### Estilização

O projeto utiliza **Tailwind CSS** para estilização:

```bash
# Instalar Tailwind (já incluído)
npm install -D tailwindcss

# Configurar Tailwind
npx tailwindcss init
```

### Configuração do Tailwind

```javascript
// tailwind.config.js
module.exports = {
  content: ["./src/**/*.{html,ts}"],
  theme: {
    extend: {},
  },
  plugins: [],
};
```

## 🧪 Testes

### Executar Testes

```bash
# Testes unitários
npm test

# Testes com coverage
npm run test:coverage

# Testes e2e (se configurado)
npm run e2e
```

### Estrutura de Testes

```
src/app/
├── auth/
│   ├── login/
│   │   ├── login.component.spec.ts
│   │   └── login.component.ts
│   └── cadastro/
│       ├── cadastro.component.spec.ts
│       └── cadastro.component.ts
└── credito/
    ├── credito/
    │   ├── credito.component.spec.ts
    │   └── credito.component.ts
    └── credito.service.spec.ts
```

## 🏗 Build

### Build de Desenvolvimento

```bash
npm run build
```

### Build de Produção

```bash
npm run build:prod
```

### Otimizações de Build

O Angular CLI aplica automaticamente:

- **Tree Shaking**: Remove código não utilizado
- **Minificação**: Comprime JavaScript e CSS
- **Bundle Splitting**: Divide o código em chunks
- **Lazy Loading**: Carrega módulos sob demanda

## 🐳 Deploy

### Docker Compose

```yaml
frontend:
  build: .
  ports:
    - "8080:80"
  environment:
    - API_URL=http://api:8050/api
  depends_on:
    - api
```

### Nginx (Produção)

```nginx
server {
    listen 80;
    server_name localhost;
    root /usr/share/nginx/html;
    index index.html;

    location / {
        try_files $uri $uri/ /index.html;
    }

    location /api {
        proxy_pass http://api:8050;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
}
```

### Kubernetes

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: frontend-creditos-fiscais
spec:
  replicas: 3
  selector:
    matchLabels:
      app: frontend-creditos-fiscais
  template:
    metadata:
      labels:
        app: frontend-creditos-fiscais
    spec:
      containers:
        - name: frontend
          image: frontend-creditos-fiscais:latest
          ports:
            - containerPort: 80
```

## 🔧 Comandos Úteis

### Desenvolvimento

```bash
# Gerar novo componente
ng generate component nome-componente

# Gerar novo serviço
ng generate service nome-servico

# Gerar novo guard
ng generate guard nome-guard

# Gerar novo interceptor
ng generate interceptor nome-interceptor
```

### Debug

```bash
# Verificar versões
ng version
node --version
npm --version

# Limpar cache
npm cache clean --force

# Reinstalar dependências
rm -rf node_modules package-lock.json
npm install
```

## 🐛 Troubleshooting

### Problemas Comuns

1. **Erro de CORS**

   ```bash
   # Verifique se o proxy está configurado
   # Verifique se a API está rodando
   curl http://localhost:8050/api/creditos/health
   ```

2. **Erro de build**

   ```bash
   # Limpe o cache
   npm cache clean --force

   # Reinstale dependências
   rm -rf node_modules package-lock.json
   npm install
   ```

3. **Erro de autenticação**
   ```bash
   # Verifique se o token está sendo enviado
   # Verifique se o interceptor está configurado
   ```

## 📊 Performance

### Otimizações Implementadas

- **Lazy Loading**: Módulos carregados sob demanda
- **OnPush Strategy**: Detecção de mudanças otimizada
- **TrackBy Functions**: Otimização de listas
- **Bundle Splitting**: Código dividido em chunks
- **Tree Shaking**: Remoção de código não utilizado

### Métricas de Performance

```bash
# Analisar bundle
npm run build:analyze

# Lighthouse audit
npm run lighthouse
```

## 🔒 Segurança

### Implementações de Segurança

- **JWT Storage**: Tokens armazenados em localStorage
- **Route Guards**: Proteção de rotas
- **HTTP Interceptors**: Interceptação de requisições
- **Input Validation**: Validação de formulários
- **XSS Protection**: Sanitização de dados

## 🤝 Contribuição

1. Fork o projeto
2. Crie uma branch (`git checkout -b feature/nova-funcionalidade`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova funcionalidade'`)
4. Push para a branch (`git push origin feature/nova-funcionalidade`)
5. Abra um Pull Request

### Padrões de Código

- **ESLint**: Linting de código
- **Prettier**: Formatação de código
- **TypeScript**: Tipagem estática
- **Angular Style Guide**: Guia de estilo oficial

## 📄 Licença

Este projeto está sob a licença MIT.

---

**Desenvolvido com ❤️ por [Seu Nome]**
