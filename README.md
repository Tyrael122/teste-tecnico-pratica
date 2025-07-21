# Microservices Investment Platform

This project was inspired by [this LinkedIn post](https://www.linkedin.com/posts/guilherme-camarao_teste-t%C3%A9cnico-activity-7340980665808486401-kZuH), featuring a technical assessment from Itaú.

This project simulates an investment platform composed of 3 main microservices and a mock quote publisher.
It uses Kafka for event streaming, Redis for caching, MySQL as the database, and Nginx as a reverse proxy.
The services are containerized and orchestrated with Docker Compose.

---

## 📦 Stack

* **Java 21 + Spring Boot**
* **Kafka (Confluent 7.6.0)**
* **Redis (8.0.3)**
* **MySQL (9.3.0)**
* **Nginx**
* **Docker Compose**

---

## 🧩 Services Overview

### 1. `microservico-operacao`

Handles user operations such as buying or selling stocks and calculating average purchase prices.

> Base Path: `/operacoes`

#### Endpoints

* `POST /{usuarioId}/criar` — Create a buy/sell operation.
* `POST /calculos/ativos/preco-medio-compra` — Calculate average price.

---

### 2. `microservico-estatisticas`

Exposes user statistics like investment totals and profit/loss. Also offers global stats for the broker.

> Base Path: `/estatisticas`

#### Endpoints

* `GET /usuario/{usuarioId}/globais`
* `GET /usuario/{usuarioId}/completo`
* `GET /corretora/globais`
* `GET /corretora/clientes`

---

### 3. `microservico-cotacoes`

Acts as a market quote service. Returns the current quote for a ticker or a list of tickers.

> Base Path: `/cotacoes`

#### Endpoints

* `GET /{tickerAtivo}` — Get latest quote.
* `POST /lote` — Get quotes for a batch of tickers.

---

### 4. `microservico-cotacoes-publisher` (Mocked)

Publishes random quotes to the Kafka topic `cotacoes-topic` to simulate live market data. This should **not** be used in production, only for testing.

---

## 🌐 Reverse Proxy

All services are exposed through **Nginx**:

| Service      | Route                                |
|--------------|--------------------------------------|
| Operações    | `http://localhost:8060/operacoes`    |
| Estatísticas | `http://localhost:8060/estatisticas` |
| Cotações     | `http://localhost:8060/cotacoes`     |

All services expose a Swagger UI at `/swagger-ui/index.html`.

---

## 🔌 Kafka

* **Topic:** `cotacoes-topic`
* Initialized via the `init-topics` container.
* Kafka UI is accessible via: [http://localhost:8080](http://localhost:8080)

---

## 🛠️ How to Run

**Create a `.env` file** at the root:

```env
MYSQL_ROOT_PASSWORD=root
MYSQL_DATABASE=investimentos
DATABASE_URL=jdbc:mysql://db:3306/investimentos?useSSL=false&allowPublicKeyRetrieval=true
REDIS_HOST=redis
REDIS_PORT=6379
KAFKA_BOOTSTRAP_SERVERS=kafka:9092
KAFKA_PORT=9092
```


To start the services:

```bash
docker compose up --build
```

To bring everything down:

```bash
docker compose down -v
```

---

### 🏗️ Architecture Overview
The project was based on this simplified architecture diagram:
<img width="1101" height="541" alt="investmentPlataform" src="https://github.com/user-attachments/assets/eed66b50-1076-4e92-afe5-1f9d7df4f0ef" />

---

## 🚦 Health Checks

All services implement Docker healthchecks with retries and timeouts. Dependencies like DBs and Kafka are waited on before booting services.

---

## ❗ Notes

* Redis, Kafka and the database are shared across all microservices.
* `microservico-cotacoes-publisher` should be used **only in dev** to mock price streams.
* Database migrations are run before the services start using the `database-migration` container.
