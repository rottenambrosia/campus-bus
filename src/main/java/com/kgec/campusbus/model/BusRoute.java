package com.kgec.campusbus.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a bus route (e.g., UP direction or DOWN direction).
 */
@Entity
@Table(name = "bus_routes")
public class BusRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String routeName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Direction direction;

    @ManyToMany
    @JoinTable(
        name = "route_stops",
        joinColumns = @JoinColumn(name = "route_id"),
        inverseJoinColumns = @JoinColumn(name = "stop_id")
    )
    @OrderBy("sequenceOrder ASC")
    private List<BusStop> stops = new ArrayList<>();

    public enum Direction {
        UP,   // Kalyani Junction → ITI More
        DOWN  // ITI More → Kalyani Junction
    }

    public BusRoute() {}

    public BusRoute(String routeName, Direction direction) {
        this.routeName = routeName;
        this.direction = direction;
    }

    // Getters and Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRouteName() { return routeName; }
    public void setRouteName(String routeName) { this.routeName = routeName; }

    public Direction getDirection() { return direction; }
    public void setDirection(Direction direction) { this.direction = direction; }

    public List<BusStop> getStops() { return stops; }
    public void setStops(List<BusStop> stops) { this.stops = stops; }
}
