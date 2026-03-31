# Screenmatch V2

Uma aplicação de linha de comando (CLI) desenvolvida em Spring Boot para buscar, salvar, gerenciar e filtrar dados de séries de TV e seus respectivos episódios. O projeto consome a OMDb API para obter as informações das obras e utiliza a API do Google Gemini para traduzir as sinopses automaticamente para o português.

## 🚀 Funcionalidades

O sistema apresenta um menu interativo com as seguintes opções de busca e gerenciamento:

* **Buscar Séries na Web**: Consome a API do OMDb para buscar dados de uma série e salva as informações no banco de dados.
* **Tradução Automática**: Durante a busca, a sinopse original em inglês é traduzida para o português (Brasil) através do modelo `gemini-2.5-flash`.
* **Buscar Episódios**: Obtém informações detalhadas de todos os episódios de todas as temporadas de uma série pesquisada.
* **Listar Séries**: Exibe todas as séries já pesquisadas e armazenadas no banco de dados local.
* **Busca por Título ou Ator**: Permite encontrar séries salvas no banco filtrando por parte do título ou pelo nome de atores.
* **Top 5 Séries**: Lista as 5 séries com as melhores avaliações do banco de dados.
* **Busca por Gênero/Categoria**: Filtra séries por categorias predefinidas (Ação, Romance, Comédia, Drama, Crime, Outros).
* **Busca Filtrada**: Permite buscar séries definindo um número máximo de temporadas e uma avaliação mínima.
* **Buscar Episódios por Trecho**: Encontra episódios cujo título contenha um trecho de texto especificado.
* **Top 5 Episódios por Série**: Retorna os 5 melhores episódios de uma série específica baseando-se na avaliação.
* **Filtrar Episódios por Data**: Exibe os episódios de uma série lançados a partir de um ano informado.

## 🛠️ Tecnologias Utilizadas

* **Java 17**
* **Spring Boot** (via `CommandLineRunner`)
* **Spring Data JPA / Hibernate** para persistência de dados
* **PostgreSQL** como banco de dados relacional
* **Maven** para gerenciamento de dependências e build
* **Jackson Databind** para conversão e manipulação de objetos JSON
* **Google GenAI SDK** (v1.43.0) para integração com a IA do Gemini
* **OMDb API** para requisição dos dados de entretenimento

## ⚙️ Pré-requisitos e Configuração

Para executar o projeto localmente, você precisará ter o **Java 17** e o **PostgreSQL** instalados em sua máquina.

### Variáveis de Ambiente
O projeto depende de algumas variáveis de ambiente para se conectar aos serviços externos e ao banco de dados. Configure as seguintes variáveis na sua IDE ou no sistema operacional:

* `OMDB_API_KEY`: Sua chave de acesso para a OMDb API.
* `GEMINI_API_KEY`: Sua chave de acesso para a API do Google Gemini (necessária para a tradução da sinopse).
* `DB_HOST`: Host do seu banco de dados PostgreSQL (ex: `localhost:5432`).
* `DB_NAME`: Nome do banco de dados criado para o projeto.
* `DB_USER`: Usuário do banco de dados.
* `DB_PASSWORD`: Senha do banco de dados.

## 🏃 Como Executar

1. Clone este repositório para sua máquina local.
2. Certifique-se de que o banco de dados PostgreSQL está rodando e as variáveis de ambiente foram configuradas.
3. Como o projeto utiliza o **Spring Boot DDL-Auto** definido como `update`, as tabelas (`series`, `episodios`) serão geradas automaticamente na primeira execução.
4. Navegue até a pasta raiz do projeto e execute a aplicação usando o Maven Wrapper embutido:

No Linux/macOS:
./mvnw spring-boot:run

No Windows:
mvnw.cmd spring-boot:run

5. Interaja com o sistema através do menu exibido no terminal.