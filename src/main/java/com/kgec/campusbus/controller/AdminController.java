package com.kgec.campusbus.controller;

import com.kgec.campusbus.repository.TicketRepository;
import com.kgec.campusbus.repository.UserRepository;
import com.kgec.campusbus.repository.BusScheduleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final BusScheduleRepository busScheduleRepository;

    public AdminController(TicketRepository ticketRepository, UserRepository userRepository, BusScheduleRepository busScheduleRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.busScheduleRepository = busScheduleRepository;
    }

    @GetMapping
    public String adminDashboard(Model model) {
        model.addAttribute("totalUsers", userRepository.count());
        model.addAttribute("totalTickets", ticketRepository.count());
        model.addAttribute("totalSchedules", busScheduleRepository.count());
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("tickets", ticketRepository.findAll());
        return "admin";
    }
}
