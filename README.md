# Aumigos - Sistema de Veterinário

## Serviços
1. Backend (Spring Boot 3)
2. Frontend nativo servido via NGINX
3. Sistema que simula pagamentos (Spring Boot 3)
4. Banco de dados PostgreSQL

## API Gateway e um Service Discovery
Optamos pelo uso do Traefik 3.6.1, que oferece tanto a API Gateway como um Service Discovery.

## Como executar
1. No diretório raíz, execute
```bash
$ docker compose build
```
```bash
$ docker compose up
```

A aplicação estará disponível em http://localhost.

Note que um volume Docker é criado, portanto os dados persistem quando o servidor é reiniciado. Para apagar o volume, desligue o servidor e execute
```bash
$ docker volume rm pgdata
```
ou
```bash
$ docker compose down -v
```