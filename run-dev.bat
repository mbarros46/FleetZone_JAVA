@echo off
echo Executando FleetZone com profile de desenvolvimento...
set SPRING_PROFILES_ACTIVE=dev
mvn spring-boot:run
pause