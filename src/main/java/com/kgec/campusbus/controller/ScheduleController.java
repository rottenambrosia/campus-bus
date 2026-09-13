package com.kgec.campusbus.controller;

import com.kgec.campusbus.model.BusRoute;
import com.kgec.campusbus.model.BusSchedule;
import com.kgec.campusbus.service.ScheduleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Displays the bus schedule timetable.
 */
@Controller
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/schedule")
    public String schedule(Model model) {
        List<BusSchedule> upSchedules = scheduleService.getSchedulesByDirection(BusRoute.Direction.UP);
        List<BusSchedule> downSchedules = scheduleService.getSchedulesByDirection(BusRoute.Direction.DOWN);

        model.addAttribute("upSchedules", upSchedules);
        model.addAttribute("downSchedules", downSchedules);
        model.addAttribute("stops", scheduleService.getAllStops());

        return "schedule";
    }
}
