FleetZone - API Java (Spring Boot)

Projeto do Challenge 2025 - Solução de mapeamento inteligente de motos para a Mottu.

Descrição
O FleetZone é uma plataforma backend desenvolvida em Java com Spring Boot, responsável por gerenciar motos, pátios e localização no ambiente da Mottu.

Integrantes
Miguel Barros - RM556652
Pedro Valentim - RM55556826
Como executar
Clone o projeto:
git clone <link-do-repositorio>
Configure o application.properties com os dados do Oracle:
spring.datasource.url=jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL
spring.datasource.username=RM556652
spring.datasource.password=120206
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.Oracle10gDialect
springdoc.swagger-ui.path=/swagger-ui.html
spring.cache.type=ehcache
Execute o projeto:
./mvnw spring-boot:run
Acesse a documentação Swagger:
http://localhost:8080/swagger-ui.html
