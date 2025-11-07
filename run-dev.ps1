# run-dev.ps1 - PowerShell helper to start app in dev mode with static dev token (for local testing only)
# Usage: Open PowerShell in project root and run: .\run-dev.ps1

$env:DEV_STATIC_TOKEN = 'dev-mobile-token-9f3b2c7a1d4e5f6a8b7c9d0e1f2a3b4c'
$env:DEV_STATIC_USER_EMAIL = 'admin@fleetzone.com'
$env:SPRING_PROFILES_ACTIVE = 'dev'
Write-Host "DEV_STATIC_TOKEN = $($env:DEV_STATIC_TOKEN)"
Write-Host "DEV_STATIC_USER_EMAIL = $($env:DEV_STATIC_USER_EMAIL)"

mvn spring-boot:run
