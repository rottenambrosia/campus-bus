package com.kgec.campusbus.repository;

import com.kgec.campusbus.model.BusRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusRouteRepository extends JpaRepository<BusRoute, Long> {

    List<BusRoute> findByDirection(BusRoute.Direction direction);
}
