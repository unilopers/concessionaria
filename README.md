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


## Configuração da API Key

A aplicação possui uma configuração dedicada para leitura da chave de autenticação pelo arquivo `src/main/resources/application.properties`.

Propriedade utilizada:

```properties
auth.api-key=concessionaria-api-key-2026
```

A leitura dessa propriedade é feita pela classe:

```text
src/main/java/com/grupo8/concessionaria/config/ApiKeyConfig.java
```

Essa classe centraliza o acesso à chave e permite que os demais componentes da autenticação consumam o valor configurado sem hardcode no código.

### Chave inicial para testes

A chave inicial cadastrada para testes no projeto é:

```text
concessionaria-api-key-2026
```

### Header padrão

Para as rotas protegidas, envie a chave no header abaixo:

```text
x-api-key: concessionaria-api-key-2026
```

### Endpoints protegidos e públicos

- Rotas de negócio (como `/clientes`, `/funcionarios`, `/vendas`, `/ordens-servico`, etc.) são protegidas por API Key.
- Rotas públicas permanecem acessíveis sem chave:
   - `/public/**`
   - `/h2-console/**`

### Resposta de erro padronizada

Quando a API Key estiver ausente ou inválida, a API responde com `401 Unauthorized` no formato:

```json
{
   "code": "API_KEY_MISSING",
   "message": "API Key ausente. Informe a chave no header x-api-key.",
   "status": 401,
   "timestamp": "2026-03-16T12:00:00Z",
   "path": "/clientes"
}
```

Para chave inválida, o campo `code` será `API_KEY_INVALID`.

### Testes de acesso

Os testes de integração validam:

- acesso negado em rota protegida sem chave;
- acesso negado em rota protegida com chave inválida;
- acesso permitido em rota protegida com chave válida;
- acesso permitido em rota pública sem chave.

Execute:

```bash
./mvnw test
```

### Como alterar a chave

Basta editar o valor da propriedade `auth.api-key` no arquivo `application.properties`.
