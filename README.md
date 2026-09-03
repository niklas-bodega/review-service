# review-service

Spring Boot-mikroservicen för **recensioner** i Niklas Bodega.

**Port:** 8086  
**Databas:** egen MySQL (`reviews`)  
**Stack:** Java 21, Spring Boot, Spring Security, JPA, JWT

## Vad den här tjänsten gör

- Skapa recension efter en bokning (`POST /api/review`) — kräver inloggning
- Hämta en recension (`GET /api/review/{reviewId}`)
- Ta bort egen recension (`DELETE /api/review/{reviewId}`)
- Medelbetyg per rumstyp för rumskorten (`GET /api/review/ratings`) — publikt
- Ett urval recensioner till startsidans karusell (`GET /api/review/showcase`) — publikt

En recension lagrar kommentar, betyg (1–5), bokningsnummer, rumstyp och användarens visningsnamn. Namnet hämtas från user-service vid skapandet och sparas lokalt (ingen join mot user-databasen).

Vid tom databas seedas några exempelrecensioner.

## Vad de andra tjänsterna gör

| Tjänst | Ansvar |
|--------|--------|
| **user-service** (8084) | Inloggning och JWT; källa till visningsnamn |
| **booking-service** (8083) | Rum och bokningar (recensioner refererar `bookingNumber` och `roomTypeId`) |
| **frontend** (8087) | Visar betyg, karusell och formulär för ny recension |

## Hur tjänsterna pratar med varandra

```
Frontend ──► review-service    ratings, showcase, skapa/ta bort recension
Frontend ──► user-service      inloggning (JWT-cookie)
Frontend ──► booking-service   rum och bokningar (frontend kopplar recension till bokning)

review-service ──GET /api/user──► user-service
  createNewReviewEntry anropar UserServiceClient.getUsername(jwt)
  och sparar namnet på recensionsraden.
```

Review-service anropar **inte** booking-service. Kopplingen till bokning/rumstyp skickas från frontend i request-body.

JWT skapas av user-service. Review-service validerar samma `JWT_SECRET`. Ratings och showcase är öppna; skapa/radera kräver autentisering.

Intern URL mot den här tjänsten i Docker: `http://review-service:8086`.  
User-service nås via `USER_SERVICE_URL` / `USER_INTERNAL_ADDRESS` (`http://user-service:8084`).

## Starta hela systemet

Tjänsten körs tillsammans med resten via Docker Compose i infra-repot. Clone alla repos som syskonmappar:

```
niklas-bodega/
├── niklas-bodega-infra/
├── user/
├── booking/
├── review-service/       ← du är här
└── frontend/
```

```bash
docker network create proxy-network   # om nätverket inte redan finns
cd ../niklas-bodega-infra
cp .env.example .env
docker compose up --build
```

I `.env`:

```env
USER_INTERNAL_ADDRESS=http://user-service:8084
VITE_REVIEW_API_URL=http://localhost:8086
```

Öppna http://localhost:8087. Review API ligger på http://localhost:8086.

Se [niklas-bodega-infra/README.md](../niklas-bodega-infra/README.md) för miljövariabler och portar.

## Köra bara den här tjänsten (IDE)

Kräver MySQL (t.ex. Compose-databasen på `localhost:3310`) och en nåbar user-service för att skapa recensioner med riktigt namn.

```bash
mvn spring-boot:run
```
