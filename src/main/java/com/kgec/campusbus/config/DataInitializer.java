package com.kgec.campusbus.config;

import com.kgec.campusbus.model.*;
import com.kgec.campusbus.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Seeds the database on application startup with bus stops, routes,
 * schedules, and demo user accounts.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final BusStopRepository stopRepository;
    private final BusRouteRepository routeRepository;
    private final BusScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(BusStopRepository stopRepository,
                           BusRouteRepository routeRepository,
                           BusScheduleRepository scheduleRepository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.stopRepository = stopRepository;
        this.routeRepository = routeRepository;
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        seedStopsAndRoutes();
        seedSchedules();
        seedUsers();
    }

    private void seedStopsAndRoutes() {
        // Real approximate coordinates around Kalyani, West Bengal
        BusStop stop1 = new BusStop("Kalyani Junction",    22.9750, 88.4345, 1);
        BusStop stop2 = new BusStop("Kalyani Ghoshpara",   22.9780, 88.4410, 2);
        BusStop stop3 = new BusStop("Academic Building",   22.9835, 88.4480, 3);
        BusStop stop4 = new BusStop("MSB / SNB",           22.9860, 88.4520, 4);
        BusStop stop5 = new BusStop("Kathaltala",          22.9900, 88.4570, 5);
        BusStop stop6 = new BusStop("ITI More",            22.9940, 88.4620, 6);

        List<BusStop> allStops = stopRepository.saveAll(
            Arrays.asList(stop1, stop2, stop3, stop4, stop5, stop6)
        );

        // UP route: Kalyani Junction → ITI More
        BusRoute upRoute = new BusRoute("Kalyani Junction → ITI More", BusRoute.Direction.UP);
        upRoute.setStops(allStops);
        routeRepository.save(upRoute);

        // DOWN route: ITI More → Kalyani Junction
        List<BusStop> reversedStops = new java.util.ArrayList<>(allStops);
        Collections.reverse(reversedStops);
        BusRoute downRoute = new BusRoute("ITI More → Kalyani Junction", BusRoute.Direction.DOWN);
        downRoute.setStops(reversedStops);
        routeRepository.save(downRoute);
    }

    private void seedSchedules() {
        List<BusRoute> routes = routeRepository.findAll();

        int busCounter = 1;
        // Create hourly schedules from 8 AM to 7 PM for each route
        for (BusRoute route : routes) {
            for (int hour = 8; hour <= 19; hour++) {
                LocalTime departureTime = LocalTime.of(hour, 0);
                String busNumber = String.format("KGEC-%03d", busCounter++);

                BusSchedule schedule = new BusSchedule(route, departureTime, busNumber, 40);
                scheduleRepository.save(schedule);
            }
        }
    }

    private void seedUsers() {
        // Demo admin account
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User(
                "admin",
                "admin@kgec.edu.in",
                passwordEncoder.encode("admin123"),
                "KGEC Admin",
                User.Role.ADMIN
            );
            userRepository.save(admin);
        }

        // Demo student account
        if (!userRepository.existsByUsername("student")) {
            User student = new User(
                "student",
                "student@kgec.edu.in",
                passwordEncoder.encode("student123"),
                "Demo Student",
                User.Role.USER
            );
            userRepository.save(student);
        }
    }
}
