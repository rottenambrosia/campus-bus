package com.kgec.campusbus.repository;

import com.kgec.campusbus.model.BusStop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusStopRepository extends JpaRepository<BusStop, Long> {

    List<BusStop> findAllByOrderBySequenceOrderAsc();
}
