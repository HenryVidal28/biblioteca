# Biblioteca API

API RESTful para gerenciar um catálogo de livros de uma biblioteca.

## Funcionalidades

- Cadastro de livros
- Listagem de todos os livros
- Atualização de um livro por ID
- Remoção de um livro por ID
- Busca de livros por título, autor ou ano de publicação

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
- **H2 Database**
- **Lombok**
- **Spring Data JPA**

## Como Executar

### Pré-requisitos

- **Java 17** instalado
- **Maven** instalado

### Passos

1. Clone o repositório:
   ```bash
   git clone <url-do-repositorio>
   
2. Navegue até o diretório do projeto:
   ```bash
   cd nome-do-projeto

3. Execute o seguinte comando para iniciar a aplicação:
   ```bash
   mvn spring-boot:run

A API estará disponível em: http://localhost:8080.

# Endpoints
Cadastro de Livro
    -POST /livro
    -Descrição: Salva um livro exigindo o preenchimento de todos os campos.
    -Body Exemplo:
        {
        "id": 1,
        "titulo": "O Guarani",
        "autor": "José de Alencar",
        "anoPublicacao": 2013,
        "isbn": "9781629732589"
        }

Listar Todos os Livros
    -GET /livro
    -Descrição: Lista todos os livros cadastrados.

Buscar Livro por ID
    -GET /livro/{id}
    -Descrição: Busca um livro por seu ID.

Atualizar Livro
    -PUT /livro/{id}
    -Descrição: Atualiza um livro existente passando seu ID e o body com os campos atualizados.
    -Body Exemplo:
        {
        "titulo": "O Guarani",
        "autor": "José de Alencar",
        "anoPublicacao": 2015,
        "isbn": "12345678910"
        }

Remover Livro
    -DELETE /livro/{id}
    -Descrição: Remove um livro cadastrado passando o ID.

Buscar Livro por Título, Autor ou Ano de Publicação
    -GET /livro/buscar
    -Descrição: Busca um livro por título, autor ou ano de publicação.
    -Parâmetros: titulo, autor, anoPublicacao (opcionais).
    -Exemplo de requisição para buscar por título:
        GET /livro/buscar?titulo=O Guarani

# Banco de Dados
A aplicação utiliza o banco de dados H2 em memória. Para acessar o console do H2, utilize:


    -URL: http://localhost:8080/h2
    -JDBC URL: jdbc:h2:mem:testdb
    -Usuário: sa
    -Senha: (deixe em branco)

Para mais informações sobre cada endpoint, a API utiliza Swagger. Acesse a documentação em: http://localhost:8080/swagger-ui.html

Esse arquivo `README.md` explica como configurar e usar a API, além de listar os principais endpoints e suas funcionalidades.

