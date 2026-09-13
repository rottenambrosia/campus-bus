package com.kgec.campusbus.model;

import jakarta.persistence.*;
import java.time.LocalTime;

/**
 * Represents a scheduled bus departure on a specific route.
 */
@Entity
@Table(name = "bus_schedules")
public class BusSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "route_id", nullable = false)
    private BusRoute route;

    @Column(nullable = false)
    private LocalTime departureTime;

    @Column(nullable = false)
    private String busNumber;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private int bookedSeats = 0;

    public BusSchedule() {}

    public BusSchedule(BusRoute route, LocalTime departureTime, String busNumber, int capacity) {
        this.route = route;
        this.departureTime = departureTime;
        this.busNumber = busNumber;
        this.capacity = capacity;
        this.bookedSeats = 0;
    }

    public boolean hasAvailableSeats() {
        return bookedSeats < capacity;
    }

    public int getAvailableSeats() {
        return capacity - bookedSeats;
    }

    // Getters and Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public BusRoute getRoute() { return route; }
    public void setRoute(BusRoute route) { this.route = route; }

    public LocalTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalTime departureTime) { this.departureTime = departureTime; }

    public String getBusNumber() { return busNumber; }
    public void setBusNumber(String busNumber) { this.busNumber = busNumber; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public int getBookedSeats() { return bookedSeats; }
    public void setBookedSeats(int bookedSeats) { this.bookedSeats = bookedSeats; }
}
