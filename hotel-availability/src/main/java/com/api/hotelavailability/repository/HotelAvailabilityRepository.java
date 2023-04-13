package com.api.hotelavailability.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.hotelavailability.domain.HotelSearchDAO;

@Repository
public interface HotelAvailabilityRepository extends JpaRepository<HotelSearchDAO, Long> {

}
