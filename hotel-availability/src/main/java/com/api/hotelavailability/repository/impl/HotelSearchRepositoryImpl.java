package com.api.hotelavailability.repository.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.api.hotelavailability.domain.HotelSearchDAO;
import com.api.hotelavailability.repository.HotelAvailabilityRepository;

@Repository
public class HotelSearchRepositoryImpl implements HotelAvailabilityRepository {
    
    private JdbcTemplate jdbcTemplate;

    public HotelSearchRepositoryImpl(JdbcTemplate jdbcTemplate) {
      this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void saveAll(List<HotelSearchDAO> hotelSearches) {
   
      jdbcTemplate.batchUpdate("INSERT INTO HOTEL_SEARCH (search_id, hotel_id, check_in, check_out, ages) " +
              "VALUES (?, ?, ?, ?, ?)",
              hotelSearches,
              100,
              (PreparedStatement ps, HotelSearchDAO hotelSearch) -> {
                ps.setString(1, hotelSearch.getSearchId());
                ps.setString(2, hotelSearch.getHotelId());
                ps.setDate(3, hotelSearch.getCheckin() != null ? Date.valueOf(hotelSearch.getCheckin()) : null);
                ps.setDate(4, hotelSearch.getCheckout() != null ? Date.valueOf(hotelSearch.getCheckout()) : null);
                ps.setArray(5, ps.getConnection().createArrayOf("int", hotelSearch.getAges().toArray()));
              });
    }

}
