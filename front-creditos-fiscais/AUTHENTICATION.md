# Sistema de Autenticação - Créditos Fiscais

## Funcionalidades Implementadas

### 1. Autenticação de Usuários

- **Login**: Endpoint `POST /api/usuarios/login`
- **Cadastro**: Endpoint `POST /api/usuarios`
- **Logout**: Remoção do token e redirecionamento

### 2. Proteção de Rotas

- **AuthGuard**: Protege rotas que requerem autenticação
- **GuestGuard**: Redireciona usuários autenticados para a página principal

### 3. Interceptor HTTP

- Adiciona automaticamente o token Bearer em todas as requisições
- Implementado usando a nova sintaxe funcional do Angular 17

### 4. Gerenciamento de Estado

- Armazenamento seguro do token no localStorage
- Observable para mudanças no estado de autenticação
- Persistência da sessão entre recarregamentos

## Estrutura de Arquivos

```
src/app/
├── auth/
│   ├── login/
│   │   ├── login.component.ts
│   │   ├── login.component.html
│   │   └── login.component.scss
│   └── cadastro/
│       ├── cadastro.component.ts
│       ├── cadastro.component.html
│       └── cadastro.component.scss
├── guards/
│   ├── auth.guard.ts
│   └── guest.guard.ts
├── interceptors/
│   └── auth.interceptor.ts
├── models/
│   └── usuario.model.ts
└── services/
    └── auth.service.ts
```

## Fluxo de Autenticação

1. **Acesso inicial**: Redireciona para `/login` se não autenticado
2. **Login**: Valida credenciais e armazena token
3. **Navegação**: Redireciona para `/credito` após login
4. **Proteção**: Todas as requisições incluem token Bearer
5. **Logout**: Remove token e redireciona para login

## Validações Implementadas

### Login

- Login obrigatório (mínimo 3 caracteres)
- Senha obrigatória (mínimo 6 caracteres)
- Tratamento de erros de credenciais

### Cadastro

- Nome obrigatório (mínimo 2 caracteres)
- Login obrigatório (mínimo 3 caracteres)
- Senha obrigatória (mínimo 6 caracteres)
- Confirmação de senha obrigatória
- Validação de senhas iguais

## Melhorias de UX

- Design responsivo com Tailwind CSS
- Feedback visual de loading
- Mensagens de erro contextuais
- Animações de transição
- Navegação intuitiva entre login e cadastro
