package com.kgec.campusbus.service;

import com.kgec.campusbus.model.*;
import com.kgec.campusbus.repository.BusScheduleRepository;
import com.kgec.campusbus.repository.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Handles ticket booking, cancellation, and fare calculation.
 */
@Service
public class TicketService {

    private static final double BASE_FARE = 5.0;   // ₹5 per stop
    private static final double MIN_FARE = 10.0;    // minimum ₹10

    private final TicketRepository ticketRepository;
    private final BusScheduleRepository scheduleRepository;

    public TicketService(TicketRepository ticketRepository,
                         BusScheduleRepository scheduleRepository) {
        this.ticketRepository = ticketRepository;
        this.scheduleRepository = scheduleRepository;
    }

    /**
     * Calculate fare based on number of stops between boarding and destination.
     */
    public double calculateFare(BusStop boarding, BusStop destination) {
        int stops = Math.abs(boarding.getSequenceOrder() - destination.getSequenceOrder());
        double fare = stops * BASE_FARE;
        return Math.max(fare, MIN_FARE);
    }

    /**
     * Book a ticket on a schedule.
     */
    @Transactional
    public Ticket bookTicket(User user, BusSchedule schedule,
                             BusStop boardingStop, BusStop destinationStop) {
        if (!schedule.hasAvailableSeats()) {
            throw new RuntimeException("No seats available on this bus");
        }

        if (boardingStop.getId().equals(destinationStop.getId())) {
            throw new RuntimeException("Boarding and destination stops cannot be the same");
        }

        double fare = calculateFare(boardingStop, destinationStop);

        // Update booked seats
        schedule.setBookedSeats(schedule.getBookedSeats() + 1);
        scheduleRepository.save(schedule);

        Ticket ticket = new Ticket(user, schedule, boardingStop, destinationStop, fare);
        return ticketRepository.save(ticket);
    }

    /**
     * Cancel a ticket and free up the seat.
     */
    @Transactional
    public Ticket cancelTicket(Long ticketId, User user) {
        Optional<Ticket> optTicket = ticketRepository.findById(ticketId);
        if (optTicket.isEmpty()) {
            throw new RuntimeException("Ticket not found");
        }

        Ticket ticket = optTicket.get();
        if (!ticket.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You can only cancel your own tickets");
        }

        if (ticket.getStatus() == Ticket.TicketStatus.CANCELLED) {
            throw new RuntimeException("Ticket is already cancelled");
        }

        ticket.setStatus(Ticket.TicketStatus.CANCELLED);

        // Free up the seat
        BusSchedule schedule = ticket.getSchedule();
        schedule.setBookedSeats(Math.max(0, schedule.getBookedSeats() - 1));
        scheduleRepository.save(schedule);

        return ticketRepository.save(ticket);
    }

    /**
     * Get all tickets for a user.
     */
    public List<Ticket> getUserTickets(User user) {
        return ticketRepository.findByUserOrderByBookingTimeDesc(user);
    }
}
