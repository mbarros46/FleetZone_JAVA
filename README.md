
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

## 🔧 Como Executar o Projeto

> 📖 **[GUIA COMPLETO DETALHADO](./COMO_EXECUTAR.md)** - Instruções passo a passo com solução de problemas

### ⚡ Execução Rápida

```bash
# 1. Clonar o repositório
git clone https://github.com/mbarros46/FleetZone_JAVA.git
cd FleetZone_JAVA

# 2. Verificar se Porta 8081 está Livre
```bash
# 3. No Windows (PowerShell)
netstat -ano | findstr :8081

# 4. Executar (Windows)
./run-dev.bat

# 4. Ou executar com Maven
mvn spring-boot:run
```

### 🌐 Acessar a Aplicação

- **URL Principal:** `http://localhost:8081/`
- **Login:** `admin@fleetzone.com` / `admin123`
- **Console H2:** `http://localhost:8081/h2-console`

### 📋 Pré-requisitos
- ☑️ **Java 17+**
- ☑️ **Maven 3.6+** 
- ☑️ **Porta 8081** livre

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

**Data de Conclusão:** 25/09/2025

- Recomendado usar banco Oracle ativo.
- Opcional: adaptar para H2 para testes locais rápidos.
- Swagger pode ser acessado em `/swagger-ui.html` após iniciar o projeto.

---

## 👨‍💻 Desenvolvido por

- Miguel Barros Ramos (RM556652)
- Pedro Valentim Merise (RM556826)
- Thomas Rodrigues (558042)

