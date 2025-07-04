[![Ask DeepWiki](https://deepwiki.com/badge.svg)](https://deepwiki.com/ZhikharevAl/simple-mock)
# NewMock Balance Service

REST API сервис для расчета баланса и лимита по клиенту. Сервис предоставляет мок-данные для тестирования и разработки.

## Описание

Сервис принимает запросы с данными клиента и возвращает рассчитанный баланс и максимальный лимит в зависимости от первой цифры идентификатора клиента:

- **8** - USD валюта, максимальный лимит $2,000.00
- **9** - EUR валюта, максимальный лимит €1,000.00
- **Остальные** - RUB валюта, максимальный лимит ₽10,000.00

Баланс генерируется случайным образом в пределах от 0 до максимального лимита.

## Технологии

- **Java 21**
- **Spring Boot 3**
- **Maven**
- **Docker & Docker Compose**
- **Swagger/OpenAPI 3** для документации API
- **Lombok** для упрощения кода
- **SLF4J** для логирования

## Структура проекта

```
src/main/java/com/example/newmock/
├── NewMockApplication.java          # Главный класс приложения
├── controller/
│   └── BalanceController.java       # REST контроллер
├── model/
│   ├── RequestDTO.java             # Модель входящего запроса
│   └── ResponseDTO.java            # Модель ответа
├── service/
│   └── BalanceService.java         # Бизнес-логика расчета баланса
└── util/
    └── BalanceCalculator.java      # Утилита для генерации случайного баланса
```

## API Endpoints

### POST /info/postBalances

Рассчитывает баланс и лимит по данным клиента.

**Запрос:**
```json
{
  "rqUID": "58dgtf565j8547f64ke7",
  "clientId": "1234567890123456789",
  "account": "12345678901234567890",
  "openDate": "2023-01-15",
  "closeDate": "2024-12-31"
}
```

**Ответ:**
```json
{
  "rqUID": "58dgtf565j8547f64ke7",
  "clientId": "1234567890123456789",
  "account": "12345678901234567890",
  "currency": "RUB",
  "balance": 7523.45,
  "maxLimit": 10000.00
}
```

### GET /info/health

Проверка состояния сервиса.

**Ответ:** `OK`

## Валидация

- `rqUID` - обязательное поле
- `clientId` - обязательное поле, ровно 19 цифр
- `account` - обязательное поле, ровно 20 цифр
- `openDate`, `closeDate` - опциональные поля в формате YYYY-MM-DD

## Запуск

### Локальный запуск

1. Убедитесь, что у вас установлен Java 21 и Maven
2. Соберите проект:
   ```bash
   mvn clean package
   ```
3. Запустите приложение:
   ```bash
   java -jar target/newMock-0.0.1-SNAPSHOT.jar
   ```

Приложение будет доступно по адресу: `http://localhost:8080`

### Запуск через Docker

1. Соберите и запустите контейнер:
   ```bash
   docker-compose up --build
   ```

Приложение будет доступно по адресу: `http://localhost:8080`

### Запуск только Docker-образа

```bash
# Сборка образа
docker build -t newmock .

# Запуск контейнера
docker run -p 8080:8080 newmock
```

## Документация API

После запуска приложения документация Swagger будет доступна по адресу:
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Примеры использования

### Curl

```bash
# Проверка здоровья сервиса
curl -X GET http://localhost:8080/info/health

# Расчет баланса для клиента с USD валютой
curl -X POST http://localhost:8080/info/postBalances \
  -H "Content-Type: application/json" \
  -d '{
    "rqUID": "test-request-123",
    "clientId": "8234567890123456789",
    "account": "12345678901234567890",
    "openDate": "2023-01-15"
  }'
```

### Различные типы валют

```bash
# USD (clientId начинается с 8)
curl -X POST http://localhost:8080/info/postBalances \
  -H "Content-Type: application/json" \
  -d '{"rqUID": "usd-test", "clientId": "8000000000000000000", "account": "12345678901234567890"}'

# EUR (clientId начинается с 9)  
curl -X POST http://localhost:8080/info/postBalances \
  -H "Content-Type: application/json" \
  -d '{"rqUID": "eur-test", "clientId": "9000000000000000000", "account": "12345678901234567890"}'

# RUB (clientId начинается с любой другой цифры)
curl -X POST http://localhost:8080/info/postBalances \
  -H "Content-Type: application/json" \
  -d '{"rqUID": "rub-test", "clientId": "1000000000000000000", "account": "12345678901234567890"}'
```

## Логирование

Сервис логирует входящие запросы и исходящие ответы в формате:
- `[RQ]` - входящий запрос
- `[RS]` - исходящий ответ
