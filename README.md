![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F)
![License](https://img.shields.io/badge/License-MIT-blue)

# Saint Louis Events Backend

A lightweight Spring Boot backend that powers **Saint Louis Events**, providing a secure proxy between the React frontend and the Ticketmaster Discovery API.

The backend centralizes API access, protects private API keys, caches responses, and exposes a simple REST endpoint for retrieving weekly event data.

---

## Features

- Spring Boot REST API
- Secure Ticketmaster API proxy
- Server-side response caching with Caffeine
- Centralized exception handling
- Environment-based configuration
- Ready for deployment on Render
- Docker support

---

## Tech Stack

- Java 17
- Spring Boot
- Spring Web
- Spring Cache
- Caffeine Cache
- Gradle
- Docker
- Render

---

## API

### Get Weekly Events

```http
GET /api/events
```

#### Query Parameters

| Parameter | Description | Example |
|-----------|-------------|---------|
| `city` | City name | `Saint Louis` |
| `start` | Start date (YYYY-MM-DD) | `2026-08-03` |
| `end` | End date (YYYY-MM-DD) | `2026-08-09` |

Example:

```text
GET /api/events?city=Saint%20Louis&start=2026-08-03&end=2026-08-09
```

The response mirrors the Ticketmaster Discovery API and is consumed by the Saint Louis Events React frontend.

---

## Configuration

Create an environment variable containing your Ticketmaster API key.

```text
TICKETMASTER_API_KEY=your_api_key_here
```

or configure it in `application.properties`.

```properties
ticketmaster.api.key=${TICKETMASTER_API_KEY}
```

---

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

---

## Docker

Build the image.

```bash
docker build -t calendar-backend .
```

Run the container.

```bash
docker run \
  -e TICKETMASTER_API_KEY=your_api_key \
  -p 8080:8080 \
  calendar-backend
```

---

## Deployment

The project includes a `render.yml` configuration for deployment to Render.

Deployments require the following environment variable:

```text
TICKETMASTER_API_KEY
```

---

## Project Structure

```text
src
└── main
    ├── java
    │   ├── controller
    │   ├── service
    │   ├── client
    │   ├── config
    │   ├── exception
    │   └── ...
    └── resources
```

---

## Related Project

**Saint Louis Events Frontend**

https://github.com/mcckyle/the-calendar

---

## License

This project is licensed under the MIT License.