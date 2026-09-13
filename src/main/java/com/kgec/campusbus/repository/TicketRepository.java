package com.kgec.campusbus.repository;

import com.kgec.campusbus.model.Ticket;
import com.kgec.campusbus.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByUserOrderByBookingTimeDesc(User user);

    List<Ticket> findByUserAndStatus(User user, Ticket.TicketStatus status);
}
