package com.api.hotelsearch.repositories;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.hotelsearch.domain.HotelSearchDAO;

@Repository
public interface HotelSearchRepository extends JpaRepository<HotelSearchDAO, Long> {

    public Optional<HotelSearchDAO> findBySearchId(String searchId);

    public long countByHotelIdAndCheckinAndCheckoutAndAges(String hotelId, LocalDate checkin, LocalDate checkout, int[] ages);
    
}
