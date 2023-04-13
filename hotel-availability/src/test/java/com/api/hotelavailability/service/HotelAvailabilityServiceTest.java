package com.api.hotelavailability.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.api.hotelavailability.domain.HotelSearchDAO;
import com.api.hotelavailability.exception.HotelAvailabilityException;
import com.api.hotelavailability.model.HotelSearch;
import com.api.hotelavailability.repository.HotelAvailabilityRepository;
import com.api.hotelavailability.service.impl.HotelAvailabilityServiceImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class HotelAvailabilityServiceTest {
    
    @Autowired
    private HotelAvailabilityRepository hotelAvailabilityRepository;
    
    private HotelAvailabilityServiceImpl hotelAvailabilityService;
    
    @BeforeAll
    public void init() {
        hotelAvailabilityService = new HotelAvailabilityServiceImpl(hotelAvailabilityRepository);
    }
    
    @Test
    public void callHotelAvailabilitySearchAndSaveOk() {
        String searchId = UUID.randomUUID().toString();
        
        hotelAvailabilityService.search(new HotelSearch("234234dsf",
                LocalDate.parse("29/12/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                LocalDate.parse("31/12/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                new int[] { 3, 29, 30, 1 }), searchId);
        
        Optional<HotelSearchDAO> hotelSearch = hotelAvailabilityRepository.findById(1L);
        
        assertTrue(hotelSearch.isPresent());
        assertEquals("234234dsf", hotelSearch.get().getHotelId());
        assertEquals(searchId, hotelSearch.get().getSearchId());
    }
    
    @Test
    public void callHotelAvailabilitySearchWithoutHotelIdAndFail() {
        String searchId = UUID.randomUUID().toString();
        
        Exception exception = assertThrows(HotelAvailabilityException.class, () -> hotelAvailabilityService.search(new HotelSearch(null,
                LocalDate.parse("29/12/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                LocalDate.parse("31/12/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                new int[] { 3, 29, 30, 1 }), searchId));
        
        assertTrue(exception.getMessage().contains("must not be blank"));
    }
    
    @Test
    public void callHotelAvailabilitySearchWithoutAgesAndFail() {
        String searchId = UUID.randomUUID().toString();
        
        Exception exception = assertThrows(HotelAvailabilityException.class, () -> hotelAvailabilityService.search(new HotelSearch("234234dsf",
                LocalDate.parse("29/12/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                LocalDate.parse("31/12/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                new int[] { }), searchId));
        
        assertTrue(exception.getMessage().contains("must not be empty"));
    }
    
    @Test
    public void callHotelAvailabilitySearchWithoutSearchIdAndFail() {
        
        Exception exception = assertThrows(HotelAvailabilityException.class, () -> hotelAvailabilityService.search(new HotelSearch("234234dsf",
                LocalDate.parse("29/12/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                LocalDate.parse("31/12/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                new int[] { 3, 29, 30, 1 }), null));
        
        assertTrue(exception.getMessage().contains("must not be blank"));
    }
    
    @Test
    public void callHotelAvailabilitySearchWithoutCheckInAndFail() {
        
        Exception exception = assertThrows(HotelAvailabilityException.class, () -> hotelAvailabilityService.search(new HotelSearch("234234dsf",
                null,
                LocalDate.parse("31/12/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                new int[] { 3, 29, 30, 1 }), null));
        
        assertTrue(exception.getMessage().contains("must not be blank"));
    }
    
    @Test
    public void callHotelAvailabilitySearchWithoutCheckOutAndFail() {
        
        Exception exception = assertThrows(HotelAvailabilityException.class, () -> hotelAvailabilityService.search(new HotelSearch("234234dsf",
                LocalDate.parse("29/12/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                null,
                new int[] { 3, 29, 30, 1 }), null));
        
        assertTrue(exception.getMessage().contains("must not be blank"));
    }
    
}
