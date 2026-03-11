# Tech Quiz Application - Admin & Player Mode

## Overview
The Emoji Tech Quiz Application has been successfully modified to support Admin and Player roles with a database backend.

## What Was Implemented

### Backend Changes (Java Spring Boot)

#### 1. Dependencies Added (pom.xml)
- `spring-boot-starter-web` - REST API support
- `spring-boot-starter-data-jpa` - Database ORM
- `com.h2database:h2` - In-memory database
- `lombok` - Reduce boilerplate code

#### 2. Database Entities
- **Player Entity** (`entity/Player.java`)
  - id (Long)
  - name (String)
  - score (int)
  - joinedAt (LocalDateTime)

- **QuizState Entity** (`entity/QuizState.java`)
  - id (Long)
  - started (boolean)

#### 3. JPA Repositories
- **PlayerRepository** - Player CRUD operations with score sorting
- **QuizStateRepository** - Quiz state management

#### 4. Services
- **PlayerService** - Business logic for player management and scoring
- **QuizService** - Business logic for quiz state management

#### 5. REST API Controllers

**PlayerController** (`/api/player`)
- `POST /api/player/join` - Player joins with name
- `GET /api/player/all` - Get all joined players
- `GET /api/player/scoreboard` - Get players sorted by score
- `POST /api/player/score` - Update player score

**QuizController** (`/api/quiz`)
- `POST /api/quiz/start` - Admin starts the quiz
- `GET /api/quiz/status` - Check if quiz has started
- `POST /api/quiz/reset` - Reset quiz state

#### 6. H2 Database Configuration
- In-memory database configured in `application.properties`
- H2 Console enabled at `/h2-console` for debugging
- Automatic schema creation on startup

### Frontend Changes (React)

#### 1. Mode Selection Screen
- Choose between Player or Admin mode

#### 2. Player Flow
- **Join Screen**: Enter name to join the quiz
- **Waiting Screen**: Wait for admin to start (polls every 3 seconds)
- **Quiz Screen**: 
  - Display emoji/icon questions
  - Timer functionality (15 seconds per question)
  - Mark answers as correct/wrong
  - Live scoreboard on the right side (auto-refresh every 3 seconds)
  - Score updates are sent to backend

#### 3. Admin Dashboard
- **Left Panel**: List of all joined players with join timestamps
- **Right Panel**: Live scoreboard sorted by score
- **Start Quiz Button**: Triggers quiz start for all players
- **Reset Quiz Button**: Reset quiz state
- Auto-refresh every 3 seconds for real-time updates

## How to Run

### 1. Build the Application
```bash
cd "Tech-quiz"
./mvnw clean install
```

### 2. Run the Spring Boot Application
```bash
./mvnw spring-boot:run
```

Or on Windows:
```bash
mvnw.cmd spring-boot:run
```

### 3. Access the Application
- **Main Application**: http://localhost:8085
- **H2 Database Console**: http://localhost:8085/h2-console
  - JDBC URL: `jdbc:h2:mem:techquizdb`
  - Username: `sa`
  - Password: (leave empty)

## Usage Instructions

### For Admin:
1. Open http://localhost:8085 in your browser
2. Click "ADMIN" button
3. Wait for players to join (list updates automatically)
4. Click "START QUIZ" button when ready
5. Monitor the live scoreboard

### For Players:
1. Open http://localhost:8085 in your browser
2. Click "PLAYER" button
3. Enter your name and click "JOIN QUIZ"
4. Wait for admin to start the quiz
5. When quiz starts, answer questions:
   - Click "START TIMER" to begin countdown
   - Click "REVEAL" to see the answer (after timer expires or manually)
   - Click "CORRECT" or "WRONG" to record your answer
6. Your score updates automatically on the scoreboard

### Multiple Players:
- Each player should open the URL in a separate device/browser
- All players can participate simultaneously
- Scores are tracked individually in the database
- Live scoreboard shows real-time rankings

## Architecture

### MVC Pattern:
- **Model**: Entity classes (Player, QuizState)
- **View**: React frontend (index.html)
- **Controller**: REST API controllers

### Technology Stack:
- **Backend**: Java 17, Spring Boot 4.0.3
- **Database**: H2 (in-memory)
- **ORM**: JPA/Hibernate
- **Frontend**: React 18 (CDN)
- **Styling**: Pure CSS

### Data Flow:
1. Player joins → POST /api/player/join → Saved to DB
2. Admin starts quiz → POST /api/quiz/start → QuizState.started = true
3. Players poll → GET /api/quiz/status → Receive quiz status
4. Answer correct → POST /api/player/score → Score updated in DB
5. All clients poll → GET /api/player/scoreboard → Real-time updates

## API Endpoints Summary

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/player/join | Join quiz with name |
| GET | /api/player/all | Get all players |
| GET | /api/player/scoreboard | Get sorted scoreboard |
| POST | /api/player/score | Update player score |
| POST | /api/quiz/start | Start quiz (admin) |
| GET | /api/quiz/status | Check quiz status |
| POST | /api/quiz/reset | Reset quiz |

## Database Schema

### Players Table
```sql
CREATE TABLE players (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    score INT NOT NULL,
    joined_at TIMESTAMP NOT NULL
);
```

### Quiz_State Table
```sql
CREATE TABLE quiz_state (
    id BIGINT PRIMARY KEY,
    started BOOLEAN NOT NULL
);
```

## Notes
- The old index.html has been backed up as `index-old.html`
- Polling intervals are set to 3 seconds for optimal balance between real-time updates and server load
- The H2 database is in-memory, so all data is lost when the server restarts
- For production, consider switching to a persistent database (PostgreSQL, MySQL)

## Troubleshooting

### Port Already in Use
If port 8085 is already in use, modify `application.properties`:
```properties
server.port=8080
```

### Database Connection Issues
Check H2 console at http://localhost:8085/h2-console with:
- JDBC URL: `jdbc:h2:mem:techquizdb`
- Username: `sa`
- Password: (empty)

### CORS Issues
If accessing from different domains, CORS is enabled with `@CrossOrigin(origins = "*")` in controllers.

## Future Enhancements
- Add authentication for admin
- Persist database to file or external DB
- Add quiz session management
- Add chat functionality
- Display question history
- Export results to CSV
- Add different quiz categories
