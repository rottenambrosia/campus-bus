# KGEC Campus Bus System

A modern, full-stack Spring Boot web application designed for Kalyani Government Engineering College (KGEC) to manage campus bus transportation. This system allows students and faculty to view real-time bus schedules, explore routes on an interactive map, and book tickets online.

## Features
- **Interactive Route Map**: Explore all bus stops across Kalyani on an integrated Leaflet.js map.
- **Real-Time Schedules**: Hourly bus schedules from 8 AM to 7 PM in both UP and DOWN directions.
- **Ticket Booking System**: Log in to select your bus, boarding stop, and destination, with automatic fare calculation.
- **Seat Availability Tracking**: Prevents overbooking by tracking available seats for each schedule.
- **User Authentication**: Secure registration and login using Spring Security and BCrypt password encryption.
- **Responsive Dark-Mode UI**: A sleek, modern interface optimized for mobile and desktop screens.
- **Ticket Management**: View your booking history and cancel tickets seamlessly.

## Tech Stack
- **Backend Framework**: Spring Boot 3.3.4 (Java 17)
- **Build Tool**: Gradle
- **Web Layer**: Spring MVC + Thymeleaf Templates
- **Security**: Spring Security
- **Database**: H2 In-Memory Database (with Spring Data JPA)
- **Frontend Map**: Leaflet.js (OpenStreetMap)
- **Styling**: Vanilla CSS (Custom Properties, Dark Mode)

## Setup & Running Locally

Since the application uses an in-memory H2 database, setup is extremely simple. No external database configuration is required.

### Prerequisites
- JDK 17 or higher
- Gradle (optional, since a Gradle wrapper is typically used)

### Steps to Run
1. Navigate to the project root directory.
2. Run the application using the Gradle wrapper:
   ```bash
   ./gradlew bootRun
   ```
   *(On Windows, use `gradlew.bat bootRun`)*

3. Open your browser and navigate to:
   [http://localhost:8080](http://localhost:8080)

### Demo Accounts
The database is automatically seeded with sample routes, stops, schedules, and two demo accounts on startup:
- **Admin User**: `admin` / `admin123`
- **Student User**: `student` / `student123`

## Database Access
You can access the H2 database console to view the seeded data while the application is running:
- **URL**: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- **JDBC URL**: `jdbc:h2:mem:campusbus`
- **Username**: `sa`
- **Password**: *(leave blank)*

## Project Structure
- `src/main/java/com/kgec/campusbus/model/` - JPA Entities (User, BusRoute, BusStop, BusSchedule, Ticket)
- `src/main/java/com/kgec/campusbus/repository/` - Spring Data JPA Repositories
- `src/main/java/com/kgec/campusbus/service/` - Business logic and fare calculation
- `src/main/java/com/kgec/campusbus/controller/` - Web Controllers for handling requests
- `src/main/java/com/kgec/campusbus/config/` - Security and Data Initialization configurations
- `src/main/resources/templates/` - Thymeleaf HTML templates
- `src/main/resources/static/` - CSS, JS, and robots.txt

## Future Enhancements
- Integration with an actual Payment Gateway (e.g., Razorpay, Stripe).
- Real-time GPS tracking for buses.
- Switch to a persistent database (MySQL/PostgreSQL) for production deployment.
- Mobile application using the provided APIs.
