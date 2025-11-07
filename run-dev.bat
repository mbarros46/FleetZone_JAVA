@echo off
echo Executando FleetZone em modo de desenvolvimento (dev static token ativado)...
REM Ajuste estes valores apenas para testes locais. NAO usar em producao.
set DEV_STATIC_TOKEN=dev-mobile-token-9f3b2c7a1d4e5f6a8b7c9d0e1f2a3b4c
set DEV_STATIC_USER_EMAIL=admin@fleetzone.com
set SPRING_PROFILES_ACTIVE=dev
echo DEV_STATIC_TOKEN=%DEV_STATIC_TOKEN%
echo DEV_STATIC_USER_EMAIL=%DEV_STATIC_USER_EMAIL%
mvn spring-boot:run
pause