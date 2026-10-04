![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F)
![License](https://img.shields.io/badge/License-MIT-blue)

# Saint Louis Events Backend

A lightweight Spring Boot API that securely connects the Saint Louis Events frontend to the Ticketmaster Discovery API.

The service acts as a server-side event proxy, keeping the Ticketmaster API key out of the browser while providing a simple endpoint for retrieving events within a requested date range.

## Overview

The backend sits between the React calendar and Ticketmaster:

```text
Saint Louis Events
       │
       │ GET /api/events
       ▼
┌──────────────────┐
│  Spring Boot API │
│                  │
│  Event Service   │
│       │          │
│       ▼          │
│ Ticketmaster     │
│ Client           │
│       │          │
│       ▼          │
│ Caffeine Cache   │
└────────┬─────────┘
         │
         ▼
 Ticketmaster API
 ```

This architecture provides a single backend boundary for external API communication while allowing the frontend to consume a purpose-built endpoint.

## Features

- **Ticketmaster proxy** - Retrieves event data without exposing the API key to clients.
- **REST API** - Provides a simple `/api/events` endpoint for the calendar.
- **Response caching** - Uses Caffeine to reduce repeated Ticketmaster requests.
- **Centralized event handling** - Converts upstream failures into consistent API responses.
- **Environment-based secrets** - Keeps the Ticketmaster API key outside source control.
- **Docker support** - Includes a production-oriented container configuration.
- **Render deployment** - Includes infrastructure configuration for deployment on Render.

## Technology

| Component | Technology |
| --- | --- |
| Language | Java 17 |
| Framework | Spring Boot 3.x |
| HTTP client | Spring `RestTemplate` |
| Caching | Spring Cache + Caffeine |
| Build system | Gradle |
| Containerization | Docker |
| Deployment | Render |

## API

### Get Events

```http
GET /api/events
```

#### Query Parameters

| Parameter | Description | Example |
|-----------|-------------|---------|
| `city` | City used for the event search | `Saint Louis` |
| `start` | Beginning date in `YYYY-MM-DD` format | `2026-08-03` |
| `end` | Ending date in `YYYY-MM-DD` format | `2026-08-09` |

Example request:

```text
GET /api/events?city=Saint%20Louis&start=2026-08-03&end=2026-08-09
```

The response contains the event data returned by the Ticketmaster Discovery API.


## Configuration

The application requires a Ticketmaster API key.

Set the key as an environment variable:

```text
TICKETMASTER_API_KEY=your_api_key_here
```

The application maps this environment variable through its configuration:

```properties
ticketmaster.api.key=${TICKETMASTER_API_KEY}
```

Never commit an actual API key to source control.

## Caching

Ticketmaster responses are cached using Caffeine.

The current cache configuration:

- Cache name: `events`
- Maximum entries: `250`
- Expiration: `10 minutes` after write

Cache keys include the requested city and date range:

```bash
city_start_end
```

For example:

```bash
Saint Louis_2026-08-03_2026-08-09
````

This prevents repeated requests for the same event range from unnecessarily reaching Ticketmaster.

## Error Handling

Upstream API failures are translated into application-level exceptions and handled centrally.

The API distinguishes between:
- Ticketmaster client errors
- Ticketmaster server errors
- Connection failures
- Unexpected application errors

Responses include a timestamp and an appropriate HTTP status.

For example, an upstream Ticketmaster failure returns:

```bash
{
  "timestamp": "2026-08-03T12:00:00",
  "error": "Ticketmaster API returned server error: 500 INTERNAL_SERVER_ERROR"
}
````

## Running Locally

Clone the repository.

```bash
git clone https://github.com/mcckyle/calendar-backend.git
cd calendar-backend
```

Start the application.

```bash
./gradlew bootRun
```

The API will be available at

```text
http://localhost:8080
```

Test the events endpoint:

```text
http://localhost:8080/api/events?city=Saint%20Louis&start=2026-08-03&end=2026-08-09
```

## Docker

Build the image:

```bash
docker build -t calendar-backend .
```

Run the container:

```bash
docker run \
  -e TICKETMASTER_API_KEY=your_api_key \
  -p 8080:8080 \
  calendar-backend
```

The API will then be available at:

```bash
http://localhost:8080
```

## Deployment

The repository includes a `render.yml` configuration for deployment on Render.

The deployment requires:

```text
TICKETMASTER_API_KEY
```

The API key should be configured as a secret environment variable in the deployment environment rather than committed to the repository.

## Project Structure

```text
calendar-backend/
├── src
│    └── main
│        ├── java
│        │   └── ...
│        │       ├── controller/
│        │       ├── service/
│        │       ├── client/
│        │       ├── config/
│        │       └── exception/
│        │
│        └── resources/
├── Dockerfile
├── render.yml
├── build.gradle
├── gradlew
├── gradlew.bat
├── settings.gradle
├── LICENSE
└── README.md
```

## Frontend

The backend powers the React frontend:

**Saint Louis Events**

https://github.com/mcckyle/the-calendar

Live application:

https://mcckyle.github.io/the-calendar/

## Development Notes

The backend intentionally keeps its public API small.

The frontend only needs to request events for a city and date range, while the backend handles:

1. External API communication.
2. API-key protection.
3. Response caching.
4. Error translation.
5. Deployment configuration.

This separation keeps the frontend lightweight while providing a clear boundary around external service access.

## License

Saint Louis Events Backend is available under the [MIT License](./LICENSE).