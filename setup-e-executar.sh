#!/bin/bash

clear
echo "=========================================="
echo "     🚀 FleetZone - Setup e Execução"
echo "=========================================="
echo

echo "⏳ Verificando pré-requisitos..."

# Verificar Java
if ! command -v java &> /dev/null; then
    echo "❌ Java não encontrado! Instale Java 17+ primeiro."
    echo "📥 Download: https://adoptium.net/temurin/releases/"
    exit 1
else
    echo "✅ Java encontrado"
    java -version
fi

# Verificar Maven
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven não encontrado! Instale Maven 3.6+ primeiro."
    echo "📥 Download: https://maven.apache.org/download.cgi"
    exit 1
else
    echo "✅ Maven encontrado"
fi

# Verificar se porta 8081 está livre
echo "⏳ Verificando porta 8081..."
if lsof -i :8081 &> /dev/null; then
    echo "⚠️  Porta 8081 está em uso!"
    echo "🔧 Tentando liberar a porta..."
    PID=$(lsof -t -i :8081)
    if [ ! -z "$PID" ]; then
        kill -9 $PID
        echo "✅ Porta 8081 liberada"
    fi
else
    echo "✅ Porta 8081 disponível"
fi

echo
echo "=========================================="
echo "     🏃‍♂️ Iniciando FleetZone..."
echo "=========================================="
echo

echo "🔧 Definindo profile de desenvolvimento..."
export SPRING_PROFILES_ACTIVE=dev

echo "🚀 Executando aplicação..."
echo
echo "➡️  A aplicação estará disponível em:"
echo "    💻 http://localhost:8081"
echo "    🔑 Login: admin@fleetzone.com / admin123"
echo "    🗄️  H2 Console: http://localhost:8081/h2-console"
echo
echo "⏹️  Para parar: Ctrl + C"
echo

mvn spring-boot:run