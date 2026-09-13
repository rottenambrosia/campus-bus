package com.kgec.campusbus.controller;

import com.kgec.campusbus.model.BusStop;
import com.kgec.campusbus.service.ScheduleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Displays the interactive map with bus stops.
 */
@Controller
public class MapController {

    private final ScheduleService scheduleService;

    public MapController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/map")
    public String map(Model model) {
        List<BusStop> stops = scheduleService.getAllStops();
        model.addAttribute("stops", stops);
        return "map";
    }
}
