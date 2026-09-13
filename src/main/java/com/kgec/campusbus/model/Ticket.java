package com.kgec.campusbus.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Represents a booked ticket for a bus schedule.
 */
@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "schedule_id", nullable = false)
    private BusSchedule schedule;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "boarding_stop_id", nullable = false)
    private BusStop boardingStop;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "destination_stop_id", nullable = false)
    private BusStop destinationStop;

    @Column(nullable = false)
    private LocalDateTime bookingTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status = TicketStatus.BOOKED;

    @Column(nullable = false)
    private double fare;

    public enum TicketStatus {
        BOOKED, CANCELLED
    }

    public Ticket() {}

    public Ticket(User user, BusSchedule schedule, BusStop boardingStop,
                  BusStop destinationStop, double fare) {
        this.user = user;
        this.schedule = schedule;
        this.boardingStop = boardingStop;
        this.destinationStop = destinationStop;
        this.fare = fare;
        this.bookingTime = LocalDateTime.now();
        this.status = TicketStatus.BOOKED;
    }

    // Getters and Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public BusSchedule getSchedule() { return schedule; }
    public void setSchedule(BusSchedule schedule) { this.schedule = schedule; }

    public BusStop getBoardingStop() { return boardingStop; }
    public void setBoardingStop(BusStop boardingStop) { this.boardingStop = boardingStop; }

    public BusStop getDestinationStop() { return destinationStop; }
    public void setDestinationStop(BusStop destinationStop) { this.destinationStop = destinationStop; }

    public LocalDateTime getBookingTime() { return bookingTime; }
    public void setBookingTime(LocalDateTime bookingTime) { this.bookingTime = bookingTime; }

    public TicketStatus getStatus() { return status; }
    public void setStatus(TicketStatus status) { this.status = status; }

    public double getFare() { return fare; }
    public void setFare(double fare) { this.fare = fare; }
}
