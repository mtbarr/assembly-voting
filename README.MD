# Desafio Técnico

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

## Tarefas Bônus

As tarefas bônus não são obrigatórias, mas nos permitem avaliar outros conhecimentos que você possa ter. É recomendado
ponderar e apresentar até onde consegue fazer, considerando o seu nível de conhecimento e a qualidade da entrega.

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

Não consegui terminar a tempo. Porém a ideia era implementar um cache para as sessões de votação,
para que a aplicação não precise acessar o banco de dados a cada requisição.

## Tarefa Bônus 3 - Mensageria e filas

Não consegui terminar a tempo. Porém a ideia era implementar um sistema de mensageria para que a aplicação não precise
contabilizar os votos de forma síncrona, o que pode ser custoso e demorado em casos de muitos votos.

### Tarefa Bônus 4 - Versionamento da API

Utilizei versionamento da API através do caminho (exemplo: `/v1/sessions`, `/v2/sessions`), o que permite que a aplicação
evolua sem quebrar a compatibilidade com versões anteriores.

As versões estão organizadas em pacotes separados, facilitando a manutenção e a introdução de novas funcionalidades de
forma independente.

---

## O que será analisado

- Simplicidade no design da solução (evitar over engineering);
- Organização do código;
- Arquitetura do projeto;
- Boas práticas de programação (manutenibilidade, legibilidade etc);
- Possíveis bugs;
- Tratamento de erros e exceções;
- Explicação breve do porquê das escolhas tomadas durante o desenvolvimento da solução;
- Uso de testes automatizados e ferramentas de qualidade;
- Limpeza do código;
- Documentação do código e da API;
- Logs da aplicação;
- Mensagens e organização dos commits.

---

## Possíveis melhorias

- Cobrir mais cenários de testes;
- Fazer com que o sistema de votação seja assincrono;
- Messageria para contabilizar os votos e apurar o resultado;

---

**Sicredi - Cooperativa de Crédito**

