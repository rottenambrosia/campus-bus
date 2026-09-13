package com.kgec.campusbus.repository;

import com.kgec.campusbus.model.BusRoute;
import com.kgec.campusbus.model.BusSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusScheduleRepository extends JpaRepository<BusSchedule, Long> {

    List<BusSchedule> findByRouteOrderByDepartureTimeAsc(BusRoute route);

    List<BusSchedule> findByRoute_DirectionOrderByDepartureTimeAsc(BusRoute.Direction direction);

    List<BusSchedule> findAllByOrderByDepartureTimeAsc();
}
