# Desafio Técnico - Sessões de Votação

## Objetivo

No cooperativismo, cada associado possui um voto, e as decisões são tomadas em assembleias por meio de votação. A partir disso,
você precisa criar uma solução back-end para gerenciar essas sessões de votação.

Essa solução deve ser executada na nuvem e disponibilizar as seguintes funcionalidades por meio de uma API REST:

- Cadastrar uma nova pauta;
- Abrir uma sessão de votação em uma pauta (a sessão de votação deve permanecer aberta pelo tempo determinado na chamada de
  abertura ou por 1 minuto por padrão);
- Receber votos dos associados em pautas (os votos são apenas 'Sim'/'Não'. Cada associado é identificado por um ID único
  e pode votar apenas uma vez por pauta);
- Contabilizar os votos e apresentar o resultado da votação na pauta.

Para fins de exercício, a segurança das interfaces pode ser abstraída, e qualquer chamada para as interfaces pode ser
considerada autorizada. A escolha da linguagem, frameworks e bibliotecas é livre (desde que não infrinja direitos
de uso).

É importante que as pautas e os votos sejam persistidos e não sejam perdidos com o reinício da aplicação.

---

## Tecnologias

- Java 17
- Spring Boot 3
- Kafka
- MySQL
- Swagger

### Tarefa Bônus 1 - Integração com Sistemas Externos

A integração para verificar se o CPF de um associado é válido pode ser ativada, e a URL do endpoint pode ser configurada
por meio do arquivo de configuração `application.yml`. Para isso, basta adicionar a seguinte propriedade:

```properties
session:
  # Verificar se o CPF do associado é válido antes de votar
  validate-cpf: true
  # URL do serviço que valida o CPF
  cpf-validator-url: "https://user-info.herokuapp.com/users"
```

#### Exemplos de retorno do serviço:

**GET /users/19839091069**

```json
{
  "status": "ABLE_TO_VOTE"
}
```

**GET /users/62289608068**

```json
{
  "status": "UNABLE_TO_VOTE"
}
```

### Tarefa Bônus 2 - Performance

Optei por realizar algumas otimizações nas consultas ao banco de dados, como a paginação de resultados e
a utilização de objetos primitivos para serem retornados, evitando a criação de objetos desnecessários.

Uma possível melhoria seria a implementação de um cache para as sessões de votação, associados e pautas, assim como
fazer com que o sistema de votação seja assíncrono.

### Tarefa Bônus 3 - Mensageria e Filas

Uma mensagem com o resultado é publicada em um tópico do Kafka quando a sessão de votação é encerrada.

### Tarefa Bônus 4 - Versionamento da API

Utilizei versionamento da API por meio do caminho (exemplo: `/v1/subjects`, `/v2/subjects`), o que permite que a
aplicação evolua sem quebrar a compatibilidade com versões anteriores.

As versões estão organizadas em pacotes separados, facilitando a manutenção e a introdução de novas funcionalidades
de forma independente.

---

## Como subir a aplicação localmente

### Requisitos

- Java 17
- Gradle
- Docker

### Clonar o repositório

```sh
git clone https://github.com/mtbarr/assembly-voting.git
```

### Executar o Docker Compose

```sh
docker-compose up -d
```

### Documentação da API

A documentação da API pode ser acessada por meio do Swagger, no endereço: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## Possíveis melhorias

- Cobrir mais cenários de testes;
- Tornar o sistema de votação assíncrono;
- Implementar cache para as sessões de votação, associados e pautas.