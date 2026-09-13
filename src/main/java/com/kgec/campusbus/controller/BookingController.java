package com.kgec.campusbus.controller;

import com.kgec.campusbus.model.*;
import com.kgec.campusbus.service.ScheduleService;
import com.kgec.campusbus.service.TicketService;
import com.kgec.campusbus.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

/**
 * Handles ticket booking and management (requires authentication).
 */
@Controller
public class BookingController {

    private final TicketService ticketService;
    private final ScheduleService scheduleService;
    private final UserService userService;

    public BookingController(TicketService ticketService,
                             ScheduleService scheduleService,
                             UserService userService) {
        this.ticketService = ticketService;
        this.scheduleService = scheduleService;
        this.userService = userService;
    }

    @GetMapping("/booking")
    public String bookingPage(Model model) {
        model.addAttribute("schedules", scheduleService.getAllSchedules());
        model.addAttribute("stops", scheduleService.getAllStops());
        return "booking";
    }

    @PostMapping("/booking")
    public String processBooking(@RequestParam Long scheduleId,
                                 @RequestParam Long boardingStopId,
                                 @RequestParam Long destinationStopId,
                                 @AuthenticationPrincipal UserDetails userDetails,
                                 RedirectAttributes redirectAttributes) {
        try {
            User user = userService.findByUsername(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            BusSchedule schedule = scheduleService.getScheduleById(scheduleId)
                    .orElseThrow(() -> new RuntimeException("Schedule not found"));

            BusStop boardingStop = scheduleService.getStopById(boardingStopId)
                    .orElseThrow(() -> new RuntimeException("Boarding stop not found"));

            BusStop destinationStop = scheduleService.getStopById(destinationStopId)
                    .orElseThrow(() -> new RuntimeException("Destination stop not found"));

            Ticket ticket = ticketService.bookTicket(user, schedule, boardingStop, destinationStop);

            redirectAttributes.addFlashAttribute("successMsg",
                    String.format("Ticket booked successfully! Ticket #%d — Fare: ₹%.0f",
                            ticket.getId(), ticket.getFare()));
            return "redirect:/my-tickets";

        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMsg", e.getMessage());
            return "redirect:/booking";
        }
    }

    @GetMapping("/my-tickets")
    public String myTickets(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Ticket> tickets = ticketService.getUserTickets(user);
        model.addAttribute("tickets", tickets);
        return "my-tickets";
    }

    @PostMapping("/cancel-ticket/{id}")
    public String cancelTicket(@PathVariable Long id,
                               @AuthenticationPrincipal UserDetails userDetails,
                               RedirectAttributes redirectAttributes) {
        try {
            User user = userService.findByUsername(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            ticketService.cancelTicket(id, user);
            redirectAttributes.addFlashAttribute("successMsg", "Ticket cancelled successfully.");

        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMsg", e.getMessage());
        }
        return "redirect:/my-tickets";
    }
}
