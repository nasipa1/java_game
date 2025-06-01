# TicTacToe WebSocket

A real-time TicTacToe game implementation using Spring Boot and WebSockets.

## Description

This project implements a multiplayer TicTacToe game where players can join and play in real-time using WebSocket communication.

## Technologies Used

- Java
- Spring Boot
- WebSockets
- Spring Security (optional)
- HTML/CSS/JavaScript for the frontend

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven

### Running the Application

1. Clone the repository
2. Navigate to the project directory
3. Run `./mvnw spring-boot:run`
4. Open your browser and go to `http://localhost:8080`

## Features

- Real-time gameplay using WebSockets
- Player matchmaking
- Game state tracking
- Win/lose/draw detection

## Project Structure

- `src/main/java/com/nasipa/tictactoewebsocket/` - Java source files
  - `config/` - Configuration classes
  - `controller/` - REST and WebSocket controllers
  - `model/` - Data models and entities
  - `repository/` - Data access layer
  - `service/` - Business logic
- `src/main/resources/` - Resources
  - `static/` - Static web assets (CSS, JS)
  - `templates/` - HTML templates
  - `application.properties` - Application configuration

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- Spring Boot team for the excellent framework
- All contributors to this project