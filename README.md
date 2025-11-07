# 🚀 FleetZone - Aplicação Web Completa

> **Aplicação Web Full-Stack** desenvolvida com **Spring Boot** para gerenciamento de motos, pátios e filiais da Mottu. Projeto implementado com **100% dos requisitos** solicitados.

## ✅ PROJETO 100% COMPLETO

✅ **Aplicação Web Completa com Spring Boot 3.2.3**  
✅ **Thymeleaf** - Frontend com templates e fragmentos  
✅ **Flyway** - Versionamento profissional do banco  
✅ **Spring Security** - Autenticação e autorização  
✅ **Funcionalidades Completas** - CRUDs + fluxos + validações  

---

## 🔐 Usuários do Sistema

### 👨‍💼 Administrador
- **Email:** `admin@fleetzone.com`
- **Senha:** `admin123`
- **Perfil:** ADMIN (acesso total ao sistema)

### 👤 Usuário Padrão
- **Email:** `user@fleetzone.com`  
- **Senha:** `user123`
- **Perfil:** USER (acesso limitado)

---

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.2.3**
- **Spring Web** (Controllers REST)
- **Spring Data JPA** (Acesso a dados)  
- **Spring Security** (Autenticação e Autorização)
- **Thymeleaf** (Frontend com Templates)
- **Flyway** (Migração e Versionamento do Banco)
- **H2 Database** (Desenvolvimento) / Oracle (Produção)
- **Bean Validation** (Validações)
- **Spring Cache** (Cache simples)
- **Bootstrap 5** (Interface responsiva)
- **Maven** (Gerenciamento de dependências)

---

## 📁 Estrutura de Pacotes

```
com.fiap.fleetzone
├── controller         // MotoController, PatioController
├── dto               // MotoDTO
├── handler           // GlobalExceptionHandler
├── model             // Entidades: Moto, Patio
├── repository        // MotoRepository, PatioRepository
└── FleetZoneApplication.java
```

---

## 🔧 Como Executar o Projeto (resumo)

As instruções essenciais para executar localmente estão aqui — não há arquivo separado `COMO_EXECUTAR.md`.

### ⚡ Execução Rápida

```powershell
# 1. Clonar o repositório
git clone https://github.com/mbarros46/FleetZone_JAVA.git
cd FleetZone_JAVA

# 2. Verificar se Porta 8085 está livre (PowerShell)
netstat -ano | findstr :8085

# 3. Executar no Windows (script fornecido) ou com Maven
.\run-dev.bat
# ou
mvn spring-boot:run
```

### 🌐 Acessar a Aplicação (local)

- **URL Principal:** `http://localhost:8085/`
- **Login:** `admin@fleetzone.com` / `admin123`
- **Console H2:** `http://localhost:8085/h2-console`

## 🔁 Perfis e Deploy (Produção)

Para rodar em produção com banco em nuvem, use o perfil `prod`:

Exportar variáveis de ambiente esperadas: DB_HOST, DB_PORT, DB_NAME, DB_USER, DB_PASS, JWT_SECRET

Exemplo (PowerShell):

```powershell
$env:DB_HOST="db.example.com";
$env:DB_PORT="5432";
$env:DB_NAME="fleetzone";
$env:DB_USER="appuser";
$env:DB_PASS="s3cret";
$env:JWT_SECRET="change-me";
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

Endpoints úteis no deploy:
- Swagger UI: /swagger-ui/index.html
- Actuator health: /actuator/health

Credenciais demo (produção)
- Email: admin@fleetzone.com
- Senha: admin123

### 📋 Pré-requisitos
- ☑️ **Java 17+**
- ☑️ **Maven 3.6+** 
 - ☑️ **Porta 8085** livre

## 🧭 Narrativa curta (para apresentação)

O FleetZone é uma solução para gestão de frotas de motocicletas para a Mottu. Centraliza cadastro de filiais, pátios e motos, permitindo operações como transferência de motos entre pátios, geração de relatórios sobre disponibilidade e controles de acesso por perfil (ADMIN/USER). A escolha de Spring Boot + Thymeleaf priorizou integração server-side segura com velocidade de desenvolvimento. Flyway garante versionamento do esquema do banco e facilita migração entre H2 (dev) e Oracle (prod).

## 📚 Evidências e arquivos importantes no repositório

- Migrações Flyway: `src/main/resources/db/migration/` (V1..V6 + pasta h2)
- Script Oracle de criação: `oracle-script.sql`
- Templates e fragmentos: `src/main/resources/templates/` (fragments, forms, login)
- Configuração e porta local: `src/main/resources/application.properties` (server.port=8085)
- Segurança e configuração: `src/main/java/com/fiap/fleetzone/security/SecurityConfig.java`
- Código-fonte organizacional: `src/main/java/com/fiap/fleetzone/` (controllers → services → repositories)

> Recomendo criar uma pasta `docs/` com canvas/protótipos e incluir o link no README antes da avaliação (opcional, pode ser feito depois).

---

## 🗃️ Versionamento do Banco com Flyway

O projeto usa **Flyway** para versionamento profissional do banco de dados:

```properties
# Configuração do Flyway
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration/h2
spring.jpa.hibernate.ddl-auto=validate
```

### 📋 Migrações Implementadas:
- **V1:** Criação das tabelas principais (users, patio, moto)
- **V2:** Inserção de pátios iniciais
- **V3:** Inserção de motos iniciais  
- **V4:** Adição da coluna status na tabela moto
- **V5:** Inserção do usuário administrador
- **V6:** Criação da tabela filial
- **V7:** Relacionamento filial-pátio
- **V8:** Colunas adicionais na filial

✅ **Todas as 8 migrações são aplicadas automaticamente na inicialização**

---

## 🎨 Funcionalidades Implementadas

### � Spring Security
- ✅ Autenticação por formulário (`/login`)
- ✅ Sistema de roles (ADMIN/USER)
- ✅ Proteção de rotas por perfil
- ✅ Logout funcional
- ✅ Controle de acesso em templates
- ✅ Senhas criptografadas (BCrypt)

### 🎭 Frontend com Thymeleaf
- ✅ Templates organizados com Bootstrap 5
- ✅ Fragmentos reutilizáveis (header, footer, menu)
- ✅ Controle de acesso por role (`sec:authorize`)
- ✅ Formulários com validação
- ✅ Mensagens de feedback (sucesso/erro)

### 📊 CRUDs Completos
- ✅ **Motos:** Listar, Criar, Editar, Excluir, Transferir
- ✅ **Pátios:** Listar, Criar, Editar, Excluir
- ✅ **Filiais:** Listar, Criar, Editar, Excluir, Detalhes

### 🚀 Fluxos Avançados
- ✅ **Transferência de Motos** entre pátios
- ✅ **Relatório de Motos por Pátio**
- ✅ **Dashboard Executivo** com visão geral
- ✅ **Integração Filial-Pátio-Moto**

---

## 📄 URLs Disponíveis

### 🌐 Interface Web
- `GET /` → Dashboard principal
- `GET /login` → Tela de login  
- `GET /motos` → CRUD de motos
- `GET /patios` → CRUD de pátios
- `GET /filiais` → CRUD de filiais

### 🔧 Desenvolvimento
- `GET /h2-console` → Console do banco H2

---

## 📡 Integração IoT (Internet of Things)

Este projeto inclui uma integração simples para receber e armazenar dados vindos de dispositivos IoT (sensores / gateways) que reportam movimentações de motos nos pátios.

Principais pontos:

- Tabela do banco: `moto_movimento` (registrando placa, pátio, endereço, timestamp e tipo de movimento)
- Script de criação: `oracle-iot-script.sql` (cria a tabela, índices e insere dados de exemplo)
- Entidade JPA: `MotoMovimento` (`src/main/java/com/fiap/fleetzone/model/MotoMovimento.java`)
- Serviço de integração: `IoTIntegrationService` — processa, vincula à `Moto`/`Patio` e persiste
- Endpoints REST para integração e consulta (controller `IoTController`):
  - POST `/api/iot/movimento` — Recebe um JSON com os dados IoT e registra um movimento
  - POST `/api/iot/simular` — Simula dados via query params (usar no frontend de teste)
  - GET  `/api/iot/recentes` — Movimentos das últimas 24 horas
  - GET  `/api/iot/historico/{placa}` — Histórico por placa
  - GET  `/api/iot/patio/{patioId}` — Movimentos de um pátio
  - GET  `/api/iot/ultimo/{placa}` — Último movimento de uma moto

Exemplo de payload JSON esperado (POST /api/iot/movimento):

```json
{
  "placa": "ABC1D23",
  "patioId": 3,
  "endereco": "Pátio da Paulista",
  "data": "2025-10-30T20:07:17"
}
```

Exemplo de envio via curl:

```bash
curl -X POST http://localhost:8085/api/iot/movimento \
  -H "Content-Type: application/json" \
  -d '{"placa":"ABC1D23","patioId":3,"endereco":"Pátio da Paulista","data":"2025-10-30T20:07:17"}'
```

Página de monitoramento (frontend): `GET /iot/monitoring` — interface em `src/main/resources/templates/iot-monitoring.html` que consome `/api/iot/recentes` e permite simular entradas.

Observações:
- Antes de usar, execute `oracle-iot-script.sql` no Oracle (usuário RM556652) para criar a tabela `moto_movimento` e índices.
- O serviço tentará associar o movimento à entidade `Moto` existente (pela placa). Se a moto existir, o pátio da moto será atualizado quando necessário.
- Tipos de movimento suportados: `ENTRADA`, `SAIDA`, `TRANSFERENCIA`.

---

## ✅ Todos os Requisitos Atendidos

- [x] **Aplicação Web Completa** - Spring Boot 3.2.3
- [x] **Frontend Thymeleaf** - Templates + fragmentos + Bootstrap
- [x] **Flyway** - 8 migrações versionando o banco profissionalmente  
- [x] **Spring Security** - Autenticação + roles + proteção de rotas
- [x] **CRUDs Completos** - Motos, Pátios, Filiais
- [x] **Fluxos Complexos** - Transferências + relatórios + dashboard
- [x] **Validações** - Bean Validation + regras de negócio
- [x] **Arquitetura** - Controllers + Services + Repositories + DTOs
- [x] **Boas Práticas** - Tratamento de erros + injeção de dependência

---

## 🏆 Status Final

**✅ PROJETO 100% COMPLETO E FUNCIONAL**

Sistema pronto para produção, atendendo todos os requisitos solicitados para a aplicação web da Mottu com Spring Boot, Thymeleaf, Flyway e Spring Security.


- Recomendado usar banco Oracle ativo.
- Opcional: adaptar para H2 para testes locais rápidos.
- Swagger pode ser acessado em `/swagger-ui.html` após iniciar o projeto.

Links importantes no deploy (preencha após deploy):
- URL do App: (https://...)
- URL do Swagger: (https://.../swagger-ui/index.html)
- URL do Actuator Health: (https://.../actuator/health)

---

## 👨‍💻 Desenvolvido por

- Miguel Barros Ramos RM556652
- Pedro Valentim Merise RM556826
- Thomas Rodrigues 558042

---

## 📱 Integração Mobile (JWT)

Para liberar o fluxo CRUD completo do app móvel recomendamos usar autenticação por JWT.

1) Login
- POST `/api/auth/login` com JSON: `{ "email": "<email>", "senha": "<senha>" }`.
- Resposta contém `token` (JWT). Use esse token no header `Authorization: Bearer <token>` nas próximas requisições.

2) Exemplo rápido (curl)
```bash
curl -X POST http://localhost:8085/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@fleetzone.com","senha":"admin123"}'
```

3) Enviando token em requests
```bash
curl -H "Authorization: Bearer <TOKEN>" http://localhost:8085/api/motos
```

4) Alternativa de desenvolvimento (dev only)
- Para testes locais rápidos você pode definir uma variável de ambiente `DEV_STATIC_TOKEN` e `DEV_STATIC_USER_EMAIL` ou colocar em `src/main/resources/application.properties` (APENAS em ambiente de desenvolvimento). Se o header `Authorization: Bearer <DEV_STATIC_TOKEN>` for enviado, o backend autentica automaticamente como o usuário definido em `DEV_STATIC_USER_EMAIL`.
- Exemplo (PowerShell):
```powershell
$env:DEV_STATIC_TOKEN = "dev-mobile-token-9f3b2c7a1d4e5f6a8b7c9d0e1f2a3b4c"
$env:DEV_STATIC_USER_EMAIL = "admin@fleetzone.com"
.\run-dev.bat
```

5) Scripts de ajuda (Windows)
- Use `run-dev.bat` (cmd) ou `run-dev.ps1` (PowerShell) para iniciar a aplicação já definindo o token dev automaticamente.

Token dev pronto para testes locais:

```
DEV_STATIC_TOKEN=dev-mobile-token-9f3b2c7a1d4e5f6a8b7c9d0e1f2a3b4c
DEV_STATIC_USER_EMAIL=admin@fleetzone.com
```

⚠️ Importante: não commit nenhum segredo ou token no repositório. Use este mecanismo apenas para desenvolvimento local.

