# Campus Bus System Documentation

## Project Overview
The Campus Bus System is a web-based application designed to facilitate the management and booking of bus services within a campus environment (e.g., between Kalyani Junction and ITI More). The platform provides a seamless experience for students and staff to view bus schedules, explore routes with interactive map displays, and book tickets for their commute. It also offers administrative capabilities to manage users, routes, and schedules efficiently.

## Project Description
Built on the Spring Boot framework, the Campus Bus System leverages modern web technologies to deliver a robust and scalable solution. The system consists of five primary entities: `User`, `BusRoute`, `BusStop`, `BusSchedule`, and `Ticket`. 
- **Users** can register, log in, view available buses, and book tickets specifying their boarding and destination stops. 
- **Routes and Stops** are carefully mapped, with each stop containing geographical coordinates (`latitude`, `longitude`) to allow visual representation on a map.
- **Schedules** dictate when buses depart on specific routes and track seat availability (`capacity`, `bookedSeats`).
- **Tickets** serve as the record of a user's booking, capturing fare details, timestamps, and the ticket status.
The system features role-based access control, distinguishing between standard users and administrators, ensuring secure data management and reliable service delivery.

---

## Level 0 Data Flow Diagram (Context Diagram)

The Context Diagram shows the system as a single process interacting with external entities.

```mermaid
flowchart TD
    User([User / Student / Staff])
    Admin([Admin])
    System((Campus Bus System))

    User -- "Login Credentials, Registration Details" --> System
    User -- "Booking Request, Search Parameters" --> System
    System -- "Ticket Confirmation, Available Schedules" --> User
    System -- "Bus Stop Map, Route Info" --> User

    Admin -- "Schedule Updates, Route Management" --> System
    System -- "System Status, Booking Reports" --> Admin
```

---

## Level 1 Data Flow Diagram (DFD)

The Level 1 DFD breaks down the main system into major sub-processes.

```mermaid
flowchart TD
    %% External Entities
    User([User])
    Admin([Admin])

    %% Processes
    P1((1.0\nUser\nAuthentication))
    P2((2.0\nRoute &\nSchedule\nManagement))
    P3((3.0\nTicket\nBooking))
    P4((4.0\nMap &\nNavigation))

    %% Data Stores
    D1[(D1: Users)]
    D2[(D2: Routes & Stops)]
    D3[(D3: Schedules)]
    D4[(D4: Tickets)]

    %% User Auth Flows
    User -- "Credentials" --> P1
    P1 <--> |"Validate / Store"| D1
    P1 -- "Auth Status" --> User

    %% Admin Management Flows
    Admin -- "Add/Edit Routes" --> P2
    Admin -- "Add/Edit Schedules" --> P2
    P2 <--> |"Update Data"| D2
    P2 <--> |"Update Data"| D3

    %% Booking Flows
    User -- "Select Schedule, Stops" --> P3
    P3 -- "Check Availability" --> D3
    P3 -- "Fetch Stops" --> D2
    P3 -- "Save Booking" --> D4
    P3 -- "Update Seat Count" --> D3
    P3 -- "Ticket Details" --> User

    %% Map Flows
    User -- "View Route on Map" --> P4
    P4 -- "Fetch Coordinates" --> D2
    P4 -- "Map Data" --> User
```

---

## Entity-Relationship (ER) Diagram

This ER diagram illustrates the database schema based on the application's models.

```mermaid
erDiagram
    USER ||--o{ TICKET : "books"
    USER {
        Long id PK
        String username
        String email
        String password
        String fullName
        Enum role
    }

    TICKET }o--|| BUS_SCHEDULE : "for"
    TICKET }o--|| BUS_STOP : "boarding at"
    TICKET }o--|| BUS_STOP : "destination at"
    TICKET {
        Long id PK
        Long user_id FK
        Long schedule_id FK
        Long boarding_stop_id FK
        Long destination_stop_id FK
        DateTime bookingTime
        Enum status
        Double fare
    }

    BUS_SCHEDULE }o--|| BUS_ROUTE : "follows"
    BUS_SCHEDULE {
        Long id PK
        Long route_id FK
        Time departureTime
        String busNumber
        Int capacity
        Int bookedSeats
    }

    BUS_ROUTE }o--o{ BUS_STOP : "includes"
    BUS_ROUTE {
        Long id PK
        String routeName
        Enum direction
    }

    BUS_STOP {
        Long id PK
        String name
        Double latitude
        Double longitude
        Int sequenceOrder
    }
```

---

## GUI Workflow

This flowchart represents the typical user navigation and interaction workflow within the application's graphical user interface.

```mermaid
flowchart TD
    Start([Landing / Home Page]) --> Auth{Logged In?}
    
    Auth -- No --> Login[Login / Register Page]
    Login --> |Success| Dashboard[User Dashboard]
    
    Auth -- Yes --> Dashboard
    
    Dashboard --> ViewSchedules[View Bus Schedules Page]
    Dashboard --> ViewMap[View Route Map Page]
    Dashboard --> ViewHistory[View Booking History]
    
    ViewMap --> ViewSchedules
    
    ViewSchedules --> SelectSchedule[Select Specific Bus Schedule]
    SelectSchedule --> BookingForm[Fill Booking Details\nSelect Boarding & Destination Stops]
    
    BookingForm --> Confirm[Confirm Booking & Fare]
    Confirm --> |Success| TicketView[View Generated Ticket]
    Confirm --> |Fail / Cancel| ViewSchedules
    
    TicketView --> Dashboard
    ViewHistory --> Dashboard
```
