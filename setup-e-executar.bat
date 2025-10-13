@echo off
cls
echo ==========================================
echo     🚀 FleetZone - Setup e Execucao
echo ==========================================
echo.

echo ⏳ Verificando pre-requisitos...

REM Verificar Java
java -version > nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Java nao encontrado! Instale Java 17+ primeiro.
    echo 📥 Download: https://adoptium.net/temurin/releases/
    pause
    exit /b 1
) else (
    echo ✅ Java encontrado
)

REM Verificar Maven
mvn -version > nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Maven nao encontrado! Instale Maven 3.6+ primeiro.
    echo 📥 Download: https://maven.apache.org/download.cgi
    pause
    exit /b 1
) else (
    echo ✅ Maven encontrado
)

REM Verificar se porta 8085 está livre
echo ⏳ Verificando porta 8085...
netstat -ano | findstr :8085 > nul 2>&1
if %errorlevel% equ 0 (
    echo ⚠️  Porta 8085 esta em uso!
    echo 🔧 Tentando liberar a porta...
    for /f "tokens=5" %%a in ('netstat -ano ^| findstr :8085') do (
        taskkill /PID %%a /F > nul 2>&1
    )
    echo ✅ Porta 8085 liberada
 ) else (
    echo ✅ Porta 8085 disponivel
)

echo.
echo ==========================================
echo     🏃‍♂️ Iniciando FleetZone...
echo ==========================================
echo.
echo 🔧 Definindo profile de desenvolvimento...
set SPRING_PROFILES_ACTIVE=dev

echo 🚀 Executando aplicacao...
echo.
echo ➡️  A aplicacao estara disponivel em:
echo    💻 http://localhost:8085
echo    🔑 Login: admin@fleetzone.com / admin123
echo    🗄️  H2 Console: http://localhost:8085/h2-console
echo.
echo ⏹️  Para parar: Ctrl + C
echo.

mvn spring-boot:run
pause