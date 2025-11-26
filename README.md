# Concessionária
- Mitsuo Nakamura Sena
- Gabriel Henrique Trindade Doná
- Davi de Vergines Guilherme
- Caio Henrique Domingues
- João Rafael Lins Menezes Tadeu

## Sobre o projeto

Este repositório contém um **Mini‑ERP de gerenciamento de concessionária**, desenvolvido em Java utilizando Spring Boot e Spring Data JPA.  
A aplicação expõe endpoints REST para cadastrar, listar, atualizar e remover informações de:

- Clientes e funcionários  
- Modelos de veículos e veículos em estoque  
- Vendas e itens de venda  
- Ordens de serviço e itens de ordem de serviço
- Movimentações financeiras  

As entidades são persistidas em um banco de dados H2 em memória durante o desenvolvimento, simplificando a configuração inicial.

## Como executar

1. Instale o Java JDK e configure a variável `JAVA_HOME`.  
2. Na raiz do projeto, execute o Maven Wrapper. Em sistemas Linux/macOS, use:

    ```bash
    ./mvnw spring-boot:run
    ```

   No Windows, use:

    ```cmd
    mvnw.cmd spring-boot:run
    ```

   O plugin do Spring Boot localizará a classe `ConcessionariaApplication` e iniciará a aplicação.

3. Com a aplicação em execução, utilize as seguintes URLs para testar os recursos:

   - `GET /clientes` — lista todos os clientes  
   - `POST /clientes` — cria um novo cliente (JSON no corpo da requisição)  
   - `GET /vendas` — lista as vendas realizadas  
   - `POST /vendas` — registra uma nova venda  

4. Para acessar o console do banco H2 e visualizar os dados, abra `http://localhost:8080/h2-console` no navegador e use:

   - **JDBC URL**: `jdbc:h2:mem:testdb`  
   - **Usuário**: `sa`  
   - **Senha**: (deixe em branco)
