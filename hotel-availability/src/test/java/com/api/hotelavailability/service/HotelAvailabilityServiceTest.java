package com.api.hotelavailability.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.api.hotelavailability.domain.HotelSearchDAO;
import com.api.hotelavailability.exception.HotelAvailabilityException;
import com.api.hotelavailability.model.HotelSearch;
import com.api.hotelavailability.repository.impl.HotelSearchRepositoryImpl;
import com.api.hotelavailability.service.impl.HotelAvailabilityServiceImpl;

@ExtendWith(SpringExtension.class)
@JdbcTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class HotelAvailabilityServiceTest {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    private HotelSearchRepositoryImpl hotelSearchRepositoryImpl;
    
    private HotelAvailabilityServiceImpl hotelAvailabilityService;
    
    @BeforeAll
    public void init() {
        hotelSearchRepositoryImpl = new HotelSearchRepositoryImpl(jdbcTemplate);
        hotelAvailabilityService = new HotelAvailabilityServiceImpl(hotelSearchRepositoryImpl);
    }
    
    @Test
    public void callHotelAvailabilitySearchAndSaveOk() {
        String searchId1 = UUID.randomUUID().toString();
        String searchId2 = UUID.randomUUID().toString();
        
        Map<String, HotelSearch> hotelSearches = new HashMap<>();
        
        hotelSearches.put(searchId1, new HotelSearch("234234dsf",
                LocalDate.of(LocalDate.now().getYear(), 12, 29),
                LocalDate.of(LocalDate.now().getYear(), 12, 31),
                List.of(3, 29, 30, 1)));
        hotelSearches.put(searchId2, new HotelSearch("4321dfhsdfsd",
                LocalDate.of(LocalDate.now().getYear(), 11, 25),
                LocalDate.of(LocalDate.now().getYear(), 11, 29),
                List.of(23, 25)));
        
        hotelAvailabilityService.searches(hotelSearches);
        
        HotelSearchDAO hotelSearch = jdbcTemplate.queryForObject(
                "SELECT * FROM HOTEL_SEARCH WHERE SEARCH_ID = ?", (rs, rowNum) ->
                new HotelSearchDAO(
                        rs.getString("search_id"),
                        rs.getString("hotel_id"),
                        rs.getDate("check_in").toLocalDate(),
                        rs.getDate("check_out").toLocalDate(),
                        Arrays.asList(rs.getArray("ages").getArray()).stream().map(i -> Integer.valueOf(i.toString())).collect(Collectors.toList())
                ), searchId1);

        assertTrue(hotelSearch != null);
        assertEquals("234234dsf", hotelSearch.getHotelId());
        assertEquals(LocalDate.of(LocalDate.now().getYear(), 12, 29), hotelSearch.getCheckin());
    }
    
    @Test
    public void callHotelAvailabilitySearchWithoutHotelIdAndFail() {
        Map<String, HotelSearch> hotelSearches = new HashMap<>();

        String searchId = UUID.randomUUID().toString();
        
        hotelSearches.put(searchId, new HotelSearch(null,
                LocalDate.of(LocalDate.now().getYear(), 12, 29),
                LocalDate.of(LocalDate.now().getYear(), 12, 31),
                List.of(3, 29, 30, 1)));
        
        Exception exception = assertThrows(HotelAvailabilityException.class, () -> hotelAvailabilityService.searches(hotelSearches));
        
        assertTrue(exception.getMessage().contains("NULL not allowed for column \"HOTEL_ID\""));
    }
    
//    @Test
//    public void callHotelAvailabilitySearchWithoutAgesAndFail() {
//        Map<String, HotelSearch> hotelSearches = new HashMap<>();
//
//        String searchId = UUID.randomUUID().toString();
//        
//        hotelSearches.put(searchId, new HotelSearch("234234dsf",
//                LocalDate.of(LocalDate.now().getYear(), 12, 29),
//                LocalDate.of(LocalDate.now().getYear(), 12, 31),
//                List.of()));
//        
//        Exception exception = assertThrows(HotelAvailabilityException.class, () -> hotelAvailabilityService.searches(hotelSearches));
//        
//        assertTrue(exception.getMessage().contains("must not be empty"));
//    }
    
    @Test
    public void callHotelAvailabilitySearchWithoutSearchIdAndFail() {
        Map<String, HotelSearch> hotelSearches = new HashMap<>();

        hotelSearches.put(null, new HotelSearch("234234dsf",
                LocalDate.of(LocalDate.now().getYear(), 12, 29),
                LocalDate.of(LocalDate.now().getYear(), 12, 31),
                List.of(3, 29, 30, 1)));
        
        Exception exception = assertThrows(HotelAvailabilityException.class, () -> hotelAvailabilityService.searches(hotelSearches));
        
        assertTrue(exception.getMessage().contains("NULL not allowed for column \"SEARCH_ID\""));
    }
    
    @Test
    public void callHotelAvailabilitySearchWithoutCheckInAndFail() {
        String searchId = UUID.randomUUID().toString();
        
        Map<String, HotelSearch> hotelSearches = new HashMap<>();
        
        hotelSearches.put(searchId, new HotelSearch("234234dsf",
                null,
                LocalDate.of(LocalDate.now().getYear(), 12, 31),
                List.of(3, 29, 30, 1)));
        
        Exception exception = assertThrows(HotelAvailabilityException.class, () -> hotelAvailabilityService.searches(hotelSearches));
        
        assertTrue(exception.getMessage().contains("NULL not allowed for column \"CHECK_IN\""));
    }
    
    @Test
    public void callHotelAvailabilitySearchWithoutCheckOutAndFail() {
        String searchId = UUID.randomUUID().toString();
        
        Map<String, HotelSearch> hotelSearches = new HashMap<>();
        
        hotelSearches.put(searchId, new HotelSearch("234234dsf",
                LocalDate.of(LocalDate.now().getYear(), 12, 29),
                null,
                List.of(3, 29, 30, 1)));
        
        Exception exception = assertThrows(HotelAvailabilityException.class, () -> hotelAvailabilityService.searches(hotelSearches));
        
        assertTrue(exception.getMessage().contains("NULL not allowed for column \"CHECK_OUT\""));
    }
    
}
