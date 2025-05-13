
# FleetZone API - Java Spring Boot

API Restful desenvolvida com Java e Spring Boot para gerenciamento de motos e pátios, como parte da Sprint 1 do Challenge FIAP 2025.

## 🚀 Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Oracle Database (com opção para H2 em desenvolvimento)
- Bean Validation
- Spring Cache (Simples ou EhCache)
- Swagger (OpenAPI)
- Maven

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

### 1. Clonar o repositório

```bash
git clone https://github.com/seu-usuario/FleetZone_JAVA.git
cd FleetZone_JAVA
```

### 2. Configurar banco de dados Oracle

Atualize `src/main/resources/application.properties`:

```
spring.datasource.url=jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.OracleDialect
spring.cache.type=simple
```

### 3. Executar com Maven

```bash
./mvnw spring-boot:run
```

Ou no VS Code com Spring Boot Extensions.

---

## 📄 Endpoints Principais

### 🚗 Motos

- `GET /motos` → Lista paginada de motos (com cache)
- `GET /motos/{id}` → Consulta moto por ID
- `POST /motos` → Cadastra nova moto
- `PUT /motos/{id}` → Atualiza moto existente
- `DELETE /motos/{id}` → Remove moto

### 🏢 Pátios

- `GET /patios`
- `POST /patios`
- `PUT /patios/{id}`
- `DELETE /patios/{id}`

---

## ✅ Requisitos Atendidos

- [x] Spring Web + Spring Data JPA
- [x] Banco Oracle configurado
- [x] CRUD completo para Moto e Patio
- [x] Relacionamento entre entidades (`@ManyToOne`, `@OneToMany`)
- [x] Bean Validation
- [x] Paginação, ordenação, busca por parâmetro
- [x] Cache (`@Cacheable`)
- [x] Boas práticas REST
- [x] DTOs usados para entrada e saída
- [x] Tratamento global de erros (`@ControllerAdvice`)
- [x] Estrutura organizada por pacotes

---

## 📌 Observações

- Recomendado usar banco Oracle ativo.
- Opcional: adaptar para H2 para testes locais rápidos.
- Swagger pode ser acessado em `/swagger-ui.html` após iniciar o projeto.

---

## 👨‍💻 Desenvolvido por

- Pedro Valentim Merisi (RMxxxxx)
- Sprint 1 - Java Advanced
