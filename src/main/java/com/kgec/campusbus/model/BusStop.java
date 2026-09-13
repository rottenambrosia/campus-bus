package com.kgec.campusbus.model;

import jakarta.persistence.*;

/**
 * Represents a bus stop with geographic coordinates for map display.
 */
@Entity
@Table(name = "bus_stops")
public class BusStop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @Column(nullable = false)
    private int sequenceOrder;

    public BusStop() {}

    public BusStop(String name, double latitude, double longitude, int sequenceOrder) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.sequenceOrder = sequenceOrder;
    }

    // Getters and Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public int getSequenceOrder() { return sequenceOrder; }
    public void setSequenceOrder(int sequenceOrder) { this.sequenceOrder = sequenceOrder; }
}
