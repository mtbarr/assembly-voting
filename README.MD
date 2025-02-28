# Desafio Técnico - Sessões de votação.
Teste feito para NTConsult/Sicredi.

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

### Tarefa Bônus 1 - Integração com Sistemas Externos

A Integração para verificar se o cpf de um associado é valido pode ser ativada e a url do endpoint pode ser configurada
através da configuração da aplicação, para isso basta adicionar a seguinte propriedade no arquivo `application.yml`:

OBS: precisei criar um serviço para simular a validação do CPF, a URL do desafio não estava funcionando.

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

Não consegui terminar a tempo. Porém a ideia era implementar um cache para as sessões de votação,
para que a aplicação não precise acessar o banco de dados a cada requisição.

## Tarefa Bônus 3 - Mensageria e filas

Uma mensagem com o resultado é publicada para um tópico do Kafka quando a sessão de votação é encerrada.

### Tarefa Bônus 4 - Versionamento da API

Utilizei versionamento da API através do caminho (exemplo: `/v1/subjects`, `/v2/subjects`), o que permite que a aplicação
evolua sem quebrar a compatibilidade com versões anteriores.

As versões estão organizadas em pacotes separados, facilitando a manutenção e a introdução de novas funcionalidades de
forma independente.

---

## Possíveis melhorias

- Cobrir mais cenários de testes;
- Fazer com que o sistema de votação seja assincrono;
- Cache para as sessões de votação, associados e pautas;

---

**Sicredi - Cooperativa de Crédito**

