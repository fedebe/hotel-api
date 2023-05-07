package com.api.hotelsearch.domain;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.Type;
import io.hypersistence.utils.hibernate.type.array.IntArrayType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;


@Entity
@Table(name = "HOTEL_SEARCH", indexes = {
        @Index(name = "HOTEL_SEARCH_ID", columnList = "id", unique = true),
        @Index(name = "HOTEL_SEARCH_SEARCH_ID", columnList = "search_id", unique = true),
        @Index(name = "HOTEL_SEARCH_HOTEL_ID", columnList = "hotel_id"),
        @Index(name = "MULT_INDEX", columnList = "hotel_id, check_in, check_out, ages")
      })
public class HotelSearchDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "search_id", nullable = false)
    private String searchId;
    
    @Column(name = "hotel_id", nullable = false)
    private String hotelId;
    
    @Column(name = "check_in", nullable = false)
    private LocalDate checkin;
    
    @Column(name = "check_out", nullable = false)
    private LocalDate checkout;
    
    @Type(IntArrayType.class)
    @Column(
        name = "ages",
        columnDefinition = "integer[]"
    )
    private int[] ages;
    
    
    public HotelSearchDAO() {}

    public HotelSearchDAO(String searchId, String hotelId, LocalDate checkin, LocalDate checkout, List<Integer> ages) {
        this.searchId = searchId;
        this.hotelId = hotelId;
        this.checkin = checkin;
        this.checkout = checkout;
        this.ages = ages.stream().mapToInt(Integer::intValue).toArray();
    }

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }

    public String getHotelId() {
        return hotelId;
    }
    
    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public LocalDate getCheckin() {
        return checkin;
    }

    public void setCheckin(LocalDate checkin) {
        this.checkin = checkin;
    }

    public LocalDate getCheckout() {
        return checkout;
    }

    public void setCheckout(LocalDate checkout) {
        this.checkout = checkout;
    }

    public int[] getAges() {
        return ages;
    }
    
    public void setAges(int[] ages) {
        this.ages = ages;
    }

}
