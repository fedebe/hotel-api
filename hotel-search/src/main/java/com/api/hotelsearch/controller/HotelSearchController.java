package com.api.hotelsearch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.hotelsearch.documentation.HotelSearchResources;
import com.api.hotelsearch.model.HotelSearch;
import com.api.hotelsearch.model.HotelSearchCount;
import com.api.hotelsearch.model.HotelSearchId;
import com.api.hotelsearch.service.HotelSearchService;

import jakarta.validation.Valid;

@RestController
public class HotelSearchController implements HotelSearchResources {

       private HotelSearchService hotelSearchService;
       
       @Autowired
       public HotelSearchController(HotelSearchService hotelSearchService) {
           this.hotelSearchService = hotelSearchService;
       }
       
       @PostMapping("/search")
       public HotelSearchId search(@RequestBody @Valid final HotelSearch hotelSearch) {
           
           return hotelSearchService.search(hotelSearch);
       }
       
       @GetMapping("/count/{searchId}")
       public HotelSearchCount count(final HotelSearchId hotelSearchId) {
           
           return hotelSearchService.count(hotelSearchId);
       }
}
