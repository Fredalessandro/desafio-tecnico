#!/bin/bash

# ===========================================
# Script de Setup - API de Créditos Fiscais
# ===========================================

echo "🚀 Iniciando setup da API de Créditos Fiscais..."
echo ""

# Verificar se o Java está instalado
echo "📋 Verificando Java..."
if ! command -v java &> /dev/null; then
    echo "❌ Java não encontrado. Por favor, instale o Java 17 ou superior."
    exit 1
fi

JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 17 ]; then
    echo "❌ Java $JAVA_VERSION encontrado. É necessário Java 17 ou superior."
    exit 1
fi

echo "✅ Java $JAVA_VERSION encontrado."

# Verificar se o Maven está instalado
echo "📋 Verificando Maven..."
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven não encontrado. Por favor, instale o Maven 3.6+."
    exit 1
fi

echo "✅ Maven encontrado."

# Verificar se o PostgreSQL está rodando
echo "📋 Verificando PostgreSQL..."
if ! pg_isready -h localhost -p 5432 &> /dev/null; then
    echo "⚠️  PostgreSQL não está rodando na porta 5432."
    echo "   Por favor, inicie o PostgreSQL antes de continuar."
    echo "   Comando para iniciar (Ubuntu/Debian): sudo systemctl start postgresql"
    echo "   Comando para iniciar (macOS): brew services start postgresql"
    echo "   Comando para iniciar (Windows): net start postgresql"
    echo ""
    read -p "Deseja continuar mesmo assim? (y/N): " -n 1 -r
    echo
    if [[ ! $REPLY =~ ^[Yy]$ ]]; then
        exit 1
    fi
else
    echo "✅ PostgreSQL está rodando."
fi

# Criar arquivo .env se não existir
echo "📋 Configurando variáveis de ambiente..."
if [ ! -f .env ]; then
    echo "📝 Criando arquivo .env..."
    cp env.example .env
    echo "✅ Arquivo .env criado. Edite-o conforme necessário."
else
    echo "✅ Arquivo .env já existe."
fi

# Compilar o projeto
echo "📋 Compilando o projeto..."
if mvn clean compile; then
    echo "✅ Projeto compilado com sucesso."
else
    echo "❌ Erro na compilação. Verifique os logs acima."
    exit 1
fi

# Executar migrações do banco
echo "📋 Executando migrações do banco de dados..."
if mvn flyway:migrate; then
    echo "✅ Migrações executadas com sucesso."
else
    echo "❌ Erro nas migrações. Verifique a conexão com o banco de dados."
    exit 1
fi

# Executar testes
echo "📋 Executando testes..."
if mvn test; then
    echo "✅ Testes executados com sucesso."
else
    echo "⚠️  Alguns testes falharam. Verifique os logs acima."
fi

echo ""
echo "🎉 Setup concluído com sucesso!"
echo ""
echo "📚 Próximos passos:"
echo "   1. Edite o arquivo .env com suas configurações"
echo "   2. Execute: mvn spring-boot:run"
echo "   3. Acesse: http://localhost:8080"
echo "   4. Teste o endpoint: http://localhost:8080/api/creditos/health"
echo ""
echo "📖 Para mais informações, consulte o README.md"
echo "" 