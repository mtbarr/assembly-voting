# Comandos CURL para a API de Votação de Pautas


## Pautas
### Buscar todas as pautas

```sh
curl -X GET "http://localhost:8080/v1/subjects?page=0"
```

### Criar uma nova pauta

```sh
curl -X POST "http://localhost:8080/v1/subjects" \
     -H "Content-Type: application/json" \
     -d '{
           "name": "Nova Pauta",
           "description": "Descrição da nova pauta"
         }'
```

### Buscar uma pauta específica

```sh
curl -X GET "http://localhost:8080/v1/subjects/bafd500f-f5b2-4255-b5fe-53d1807c8ff6"
```


## Votos
### Votar em um assunto


```sh
curl -X POST "http://localhost:8080/v1/votes" \
     -H "Content-Type: application/json" \
     -d '{
           "associateId": "123e4567-e89b-12d3-a456-426614174000",
           "subjectId": "bafd500f-f5b2-4255-b5fe-53d1807c8ff6",
           "voteType": "YES"
         }'
```

### Buscar um voto específico

```sh
curl -X GET "http://localhost:8080/v1/votes/123e4567-e89b-12d3-a456-426614174000"
```

### Buscar votos de um assunto

```sh
curl -X GET "http://localhost:8080/v1/votes/subject/123e4567-e89b-12d3-a456-426614174000?page=0&size=10&orderBy=createdAt&direction=ASC"
```

## Sessões de Votação

### Criar uma nova sessão de votação

```sh
curl -X POST "http://localhost:8080/v1/sessions" \
     -H "Content-Type: application/json" \
     -d '{
           "subjectId": "123e4567-e89b-12d3-a456-426614174000"
         }'
```

### Buscar resultado de votos de um assunto

```sh
curl -X GET "http://localhost:8080/v1/sessions/result/123e4567-e89b-12d3-a456-426614174000"
```

## Associados

### Listar todos os associados

```sh
curl -X GET "http://localhost:8080/v1/associates?page=0&size=10&orderBy=createdAt&direction=ASC"
```

### Criar um novo associado

```sh
curl -X POST "http://localhost:8080/v1/associates" \
     -H "Content-Type: application/json" \
     -d '{
           "name": "Nome do Associado",
           "cpf": "12345678900"
         }'
```

### Buscar um associado por ID

```sh
curl -X GET "http://localhost:8080/v1/associates/123e4567-e89b-12d3-a456-426614174000"
```

