package com.kgec.campusbus.service;

import com.kgec.campusbus.model.BusRoute;
import com.kgec.campusbus.model.BusSchedule;
import com.kgec.campusbus.model.BusStop;
import com.kgec.campusbus.repository.BusRouteRepository;
import com.kgec.campusbus.repository.BusScheduleRepository;
import com.kgec.campusbus.repository.BusStopRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Manages bus schedules, routes, and stops.
 */
@Service
public class ScheduleService {

    private final BusScheduleRepository scheduleRepository;
    private final BusRouteRepository routeRepository;
    private final BusStopRepository stopRepository;

    public ScheduleService(BusScheduleRepository scheduleRepository,
                           BusRouteRepository routeRepository,
                           BusStopRepository stopRepository) {
        this.scheduleRepository = scheduleRepository;
        this.routeRepository = routeRepository;
        this.stopRepository = stopRepository;
    }

    public List<BusSchedule> getAllSchedules() {
        return scheduleRepository.findAllByOrderByDepartureTimeAsc();
    }

    public List<BusSchedule> getSchedulesByDirection(BusRoute.Direction direction) {
        return scheduleRepository.findByRoute_DirectionOrderByDepartureTimeAsc(direction);
    }

    public Optional<BusSchedule> getScheduleById(Long id) {
        return scheduleRepository.findById(id);
    }

    public List<BusRoute> getAllRoutes() {
        return routeRepository.findAll();
    }

    public List<BusStop> getAllStops() {
        return stopRepository.findAllByOrderBySequenceOrderAsc();
    }

    public Optional<BusStop> getStopById(Long id) {
        return stopRepository.findById(id);
    }
}
