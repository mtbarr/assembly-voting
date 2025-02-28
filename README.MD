# Desafio Técnico - Sessões de votação.

## Objetivo

No cooperativismo, cada associado possui um voto e as decisões são tomadas em assembleias, por votação. A partir disso,
você precisa criar uma solução back-end para gerenciar essas sessões de votação.

Essa solução deve ser executada na nuvem e promover as seguintes funcionalidades através de uma API REST:

- Cadastrar uma nova pauta;
- Abrir uma sessão de votação em uma pauta (a sessão de votação deve ficar aberta por um tempo determinado na chamada de
  abertura ou 1 minuto por default);
- Receber votos dos associados em pautas (os votos são apenas 'Sim'/'Não'. Cada associado é identificado por um id único
  e pode votar apenas uma vez por pauta);
- Contabilizar os votos e dar o resultado da votação na pauta.

Para fins de exercício, a segurança das interfaces pode ser abstraída e qualquer chamada para as interfaces pode ser
considerada como autorizada. A escolha da linguagem, frameworks e bibliotecas é livre (desde que não infrinja direitos
de uso).

É importante que as pautas e os votos sejam persistidos e que não sejam perdidos com o restart da aplicação.

---

## Tecnologias

- Java 17
- Spring Boot 3
- Kafka
- MySQL
- Swagger

### Tarefa Bônus 1 - Integração com Sistemas Externos

A Integração para verificar se o cpf de um associado é valido pode ser ativada e a url do endpoint pode ser configurada
através da configuração da aplicação, para isso basta adicionar a seguinte propriedade no arquivo `application.yml`:

```properties
session:
# Verificar se o CPF do associado é válido antes de votar
validate-cpf:true
# URL do serviço que valida o CPF
cpf-validator-url:"https://user-info.herokuapp.com/users"

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

Optei por fazer algumas otimizações nas consultas ao banco de dados, como por exemplo, a paginação de resultados e
a utilização de objetos primitivos para serem retornados nas consultas, evitando a criação de objetos desnecessários.

Uma possivel melhoria seria implementar um cache para as sessões de votação, associados e pautas assim como
fazer com que o sistema de votação seja assincrono.

### Tarefa Bônus 3 - Mensageria e filas

Uma mensagem com o resultado é publicada para um tópico do Kafka quando a sessão de votação é encerrada.

### Tarefa Bônus 4 - Versionamento da API

Utilizei versionamento da API através do caminho (exemplo: `/v1/subjects`, `/v2/subjects`), o que permite que a
aplicação
evolua sem quebrar a compatibilidade com versões anteriores.

As versões estão organizadas em pacotes separados, facilitando a manutenção e a introdução de novas funcionalidades de
forma independente.

---

## Como subir a aplicação localmente.

### Clonar o repositório

#### Requisitos

- Java 17
- Gradle
- Docker

### Clone o repositório

```
git clone https://github.com/mtbarr/assembly-voting.git
```

### Executar o docker-compose

```shell
docker-compose up -d
```

### Documentação da API

A documentação da API pode ser acessada através do Swagger, no endereço: http://localhost:8080/swagger-ui/index.html

---

## Possíveis melhorias

- Cobrir mais cenários de testes;
- Fazer com que o sistema de votação seja assincrono;
- Cache para as sessões de votação, associados e pautas;

---
