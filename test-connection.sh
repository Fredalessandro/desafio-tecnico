#!/bin/bash

echo "=== Testando conexão com PostgreSQL ==="

# Verificar se o container do PostgreSQL está rodando
echo "1. Verificando status do container PostgreSQL..."
docker ps | grep postgres-db

if [ $? -eq 0 ]; then
    echo "✅ Container PostgreSQL está rodando"
else
    echo "❌ Container PostgreSQL não está rodando"
    exit 1
fi

# Testar conexão direta com PostgreSQL
echo "2. Testando conexão direta com PostgreSQL..."
docker exec postgres-db pg_isready -U postgres

if [ $? -eq 0 ]; then
    echo "✅ PostgreSQL está respondendo"
else
    echo "❌ PostgreSQL não está respondendo"
    exit 1
fi

# Verificar se a API está rodando
echo "3. Verificando status do container da API..."
docker ps | grep api-creditos-fiscais

if [ $? -eq 0 ]; then
    echo "✅ Container da API está rodando"
else
    echo "❌ Container da API não está rodando"
    exit 1
fi

# Testar endpoint de health da API
echo "4. Testando endpoint de health da API..."
sleep 5
curl -f http://localhost:8050/api/creditos/health

if [ $? -eq 0 ]; then
    echo "✅ API está respondendo corretamente"
else
    echo "❌ API não está respondendo"
    echo "Verificando logs da API..."
    docker logs api-creditos-fiscais --tail 20
fi

echo "=== Teste concluído ===" 