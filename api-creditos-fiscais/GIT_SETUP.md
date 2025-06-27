# 🚀 Guia de Primeira Publicação no Git

Este guia contém todas as instruções necessárias para fazer a primeira publicação do projeto no Git.

## 📋 Pré-requisitos

- Git instalado no seu computador
- Conta no GitHub, GitLab ou outro serviço de hospedagem Git
- Acesso ao terminal/linha de comando

## 🔧 Configuração Inicial do Git

### 1. Configurar suas credenciais do Git

```bash
# Configurar seu nome de usuário
git config --global user.name "Seu Nome Completo"

# Configurar seu email
git config --global user.email "seu.email@exemplo.com"

# Verificar as configurações
git config --list
```

### 2. Inicializar o repositório Git (se ainda não foi feito)

```bash
# Navegar para a pasta do projeto
cd /caminho/para/seu/projeto

# Inicializar o repositório Git
git init
```

## 📝 Preparação para o Primeiro Commit

### 1. Verificar o status do repositório

```bash
# Ver quais arquivos estão sendo rastreados
git status
```

### 2. Adicionar todos os arquivos ao staging

```bash
# Adicionar todos os arquivos
git add .

# Ou adicionar arquivos específicos
git add README.md
git add pom.xml
git add src/
```

### 3. Verificar o que será commitado

```bash
# Ver os arquivos que estão no staging
git status

# Ver as diferenças dos arquivos
git diff --cached
```

## 🎯 Primeiro Commit

### 1. Fazer o primeiro commit

```bash
# Commit inicial com mensagem descritiva
git commit -m "feat: Implementação inicial da API de Créditos Fiscais

- Configuração do projeto Spring Boot 3.3.12
- Implementação dos endpoints REST para consulta de créditos
- Configuração do banco PostgreSQL com Flyway
- Estrutura de camadas (Controller, Service, Repository)
- Tratamento de exceções global
- Documentação completa da API
- Scripts de migração do banco de dados"
```

### 2. Verificar o commit

```bash
# Ver o histórico de commits
git log --oneline

# Ver detalhes do último commit
git show
```

## 🌐 Configuração do Repositório Remoto

### 1. Criar repositório no GitHub/GitLab

1. Acesse [GitHub](https://github.com) ou [GitLab](https://gitlab.com)
2. Clique em "New repository" ou "Novo projeto"
3. Configure o repositório:
   - **Nome**: `api-creditos-fiscais` ou `backend`
   - **Descrição**: "API REST para gerenciamento de créditos fiscais"
   - **Visibilidade**: Público ou Privado (sua escolha)
   - **NÃO** inicialize com README (já temos um)
   - **NÃO** adicione .gitignore (já temos um)
   - **NÃO** adicione licença (opcional)

### 2. Conectar o repositório local ao remoto

```bash
# Adicionar o repositório remoto (substitua pela URL do seu repositório)
git remote add origin https://github.com/seu-usuario/nome-do-repositorio.git

# Verificar se foi adicionado corretamente
git remote -v
```

## 📤 Primeira Publicação (Push)

### 1. Fazer o push inicial

```bash
# Fazer o primeiro push para a branch main
git push -u origin main

# Se sua branch padrão for 'master', use:
git push -u origin master
```

### 2. Verificar se foi publicado

```bash
# Verificar o status
git status

# Verificar as branches remotas
git branch -r
```

## 🔄 Fluxo de Trabalho Diário

### Comandos básicos para uso diário:

```bash
# Ver status das mudanças
git status

# Adicionar mudanças
git add .

# Fazer commit
git commit -m "feat: descrição da mudança"

# Fazer push
git push

# Atualizar repositório local
git pull
```

## 🏷️ Convenções de Commit

Use estas convenções para mensagens de commit:

- `feat:` - Nova funcionalidade
- `fix:` - Correção de bug
- `docs:` - Documentação
- `style:` - Formatação de código
- `refactor:` - Refatoração de código
- `test:` - Adição de testes
- `chore:` - Tarefas de manutenção

Exemplo:

```bash
git commit -m "feat: adiciona endpoint para buscar créditos por NFS-e"
git commit -m "fix: corrige validação de parâmetros no controller"
git commit -m "docs: atualiza README com instruções de instalação"
```

## 🛡️ Boas Práticas

### 1. Sempre verifique antes de commitar

```bash
# Ver o que será commitado
git diff --cached

# Ver arquivos não rastreados
git status
```

### 2. Use branches para novas funcionalidades

```bash
# Criar nova branch
git checkout -b feature/nova-funcionalidade

# Trabalhar na branch
# ... fazer mudanças ...

# Fazer commit
git add .
git commit -m "feat: implementa nova funcionalidade"

# Voltar para main
git checkout main

# Fazer merge
git merge feature/nova-funcionalidade
```

### 3. Mantenha o repositório limpo

```bash
# Remover branches locais não utilizadas
git branch -d nome-da-branch

# Limpar arquivos não rastreados (cuidado!)
git clean -fd
```

## 🚨 Solução de Problemas Comuns

### Erro de autenticação

```bash
# Configurar credenciais (GitHub)
git config --global credential.helper store
# Na próxima vez que fizer push, digite seu usuário e token
```

### Conflito de merge

```bash
# Ver conflitos
git status

# Resolver conflitos manualmente nos arquivos
# Depois adicionar e commitar
git add .
git commit -m "fix: resolve conflitos de merge"
```

### Desfazer último commit

```bash
# Desfazer commit mantendo mudanças
git reset --soft HEAD~1

# Desfazer commit descartando mudanças
git reset --hard HEAD~1
```

## ✅ Checklist Final

Antes de publicar, verifique se:

- [ ] README.md está completo e atualizado
- [ ] .gitignore está configurado corretamente
- [ ] Todos os arquivos necessários estão no repositório
- [ ] Não há arquivos sensíveis (senhas, chaves) no código
- [ ] O projeto compila sem erros
- [ ] Os testes passam
- [ ] A documentação está clara

## 🎉 Próximos Passos

Após a primeira publicação:

1. **Configurar CI/CD** (GitHub Actions, GitLab CI)
2. **Adicionar badges** no README
3. **Configurar proteção de branches**
4. **Criar issues** para próximas funcionalidades
5. **Configurar ambiente de desenvolvimento** para a equipe

---

**Dica:** Mantenha este arquivo atualizado conforme o projeto evolui!
