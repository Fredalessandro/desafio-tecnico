@echo off
chcp 65001 >nul

echo ===========================================
echo Script de Setup - API de Créditos Fiscais
echo ===========================================
echo.

echo 📋 Verificando Java...
java -version >nul 2>&1
if errorlevel 1 (
    echo ❌ Java não encontrado. Por favor, instale o Java 17 ou superior.
    pause
    exit /b 1
)
echo ✅ Java encontrado.

echo 📋 Verificando Maven...
mvn -version >nul 2>&1
if errorlevel 1 (
    echo ❌ Maven não encontrado. Por favor, instale o Maven 3.6+.
    pause
    exit /b 1
)
echo ✅ Maven encontrado.

echo 📋 Configurando variáveis de ambiente...
if not exist .env (
    echo 📝 Criando arquivo .env...
    copy env.example .env >nul
    echo ✅ Arquivo .env criado. Edite-o conforme necessário.
) else (
    echo ✅ Arquivo .env já existe.
)

echo 📋 Compilando o projeto...
call mvn clean compile
if errorlevel 1 (
    echo ❌ Erro na compilação. Verifique os logs acima.
    pause
    exit /b 1
)
echo ✅ Projeto compilado com sucesso.

echo 📋 Executando migrações do banco de dados...
call mvn flyway:migrate
if errorlevel 1 (
    echo ❌ Erro nas migrações. Verifique a conexão com o banco de dados.
    pause
    exit /b 1
)
echo ✅ Migrações executadas com sucesso.

echo 📋 Executando testes...
call mvn test
if errorlevel 1 (
    echo ⚠️  Alguns testes falharam. Verifique os logs acima.
) else (
    echo ✅ Testes executados com sucesso.
)

echo.
echo 🎉 Setup concluído com sucesso!
echo.
echo 📚 Próximos passos:
echo    1. Edite o arquivo .env com suas configurações
echo    2. Execute: mvn spring-boot:run
echo    3. Acesse: http://localhost:8050
echo    4. Teste o endpoint: http://localhost:8080/api/creditos/health
echo.
echo 📖 Para mais informações, consulte o README.md
echo.
pause 