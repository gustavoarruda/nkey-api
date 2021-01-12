# nkey-api
Este projeto foi criado através de um processo seletivo na empresa NKey Soluções Digitais

## Arquitetura

### Versão Java

Foi utilizado o 'openjdk:8-jdk' para o desenvolvimento deste teste.

### Frameworks

Foi utilizado como base do projeto o Spring Boot 2.2.6. Algumas bibliotecas adicionais, da própria suite do spring:

* ** spring-boot-starter-web - Core do Spring Boot para API's
* ** spring-boot-devtools - Utilitários do Spring Boot para desenvolvedores
* ** spring-boot-starter-data-mongodb - Este é uma biblioteca do Spring Data com foco no banco de dados MongoDB.
* ** spring-boot-starter-security - Biblioteca que permite trabalhar com autenticação no Spring.
* ** jjwt - Framework para criar tokens e transmitir informações de maneira mais segura.
* ** javafaker - Biblioteca utilitária para geração de dados aleatórios para testes.
* ** springfox-swagger2 - É uma ferramenta open source desenvolvida para integrar projetos Spring Boot com a especificação Swagger afim de documentar a API.
* ** hibernate-validator - Biblioteca adicional do Hibernate para adicionar os validadores JPA do Hibernate
* ** lombok - Biblioteca utilitária para geração automática de getter's, setter's, builder's, entre outros.
* ** org.apache.commons (commons-lang3) - Biblioteca da Apache, com diversas classes utilitárias

Bibliotecas de teste:

* ** de.flapdoodle.embed.mongo - É um projeto open source que tem como objetivo prover uma plataforma para rodar testes unitários no MongoDB
* ** junit-vintage-engine - JUnit é uma API de open source para a criação de testes unitários em Java.
* ** spring-boot-starter-test - Core do Spring Boot para testes em microserviços.


### Definição de pacotes
* br
  * com
    * nkey	
      * api
        * config - Classes de configuração da aplicação
		* exceptionshandler - Todas as exceptions interceptadas do sistema
		* jwt - Todas as classes de construção e autenticação da api
        * model - Entidades
        * repository - Todas as classes de acesso a dados do sistema. Aqui foi usada a interface *MongoRepository*.
        * resources - Endpoints do projeto. Expõe os métodos públicos dos services como resources.
        * service - Classes de regra de negócio. 
		* sequence - Classe que cria a sequence para as Collections para o MongoDb

		
### Conteinerização

Foi configurado também para este projeto, um Dockerfile simples, que consiste em pegar o jar gerado pelo projeto, que já é auto-executável, e embutir em uma imagem docker junto do openjdk 8. A imagem compilada está disponível em: https://hub.docker.com/repository/docker/gustavoanalistabr/nkey-api

# Instruções docker:

> docker build nkey-api:latest --tag nkey-api:0.0.1-SNAPSHOT .
> docker run -d -p 8080:8080 nkey-api
> docker push nkey-api:latest

* Para baixar no repositorio:

> docker pull gustavoanalistabr/nkey-api

### Swagger

Para acessar o swagger do projeto, basta executar o projeto e acessar a url: http://localhost:8080/swagger-ui.html. O mesmo é dinâmico, gerado pelas anotações presentes nas classes de *Resources*.
## Testes automatizados

Neste projeto, escrevemos os testes em dois níveis: *Service* e *Resource*. 

Em *Service*, testamos unitariamente cada um dos serviços em detalhes de suas regras de negócio, incluindo as validações.

Já em *Resource*, testamos as API's REST e suas fronteiras, testando o dado que entra, o dado que sai, e seus respectivos códigos HTTP. Fechando, desta maneira, todo o ciclo de API's.