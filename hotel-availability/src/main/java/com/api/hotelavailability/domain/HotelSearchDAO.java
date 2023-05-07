package com.api.hotelavailability.domain;

import java.time.LocalDate;
import java.util.List;

public class HotelSearchDAO {

    private String searchId;

    private String hotelId;

    private LocalDate checkin;

    private LocalDate checkout;

    private List<Integer> ages;

    public HotelSearchDAO() {
    }

    public HotelSearchDAO(String searchId, String hotelId, LocalDate checkin, LocalDate checkout, List<Integer> ages) {
        this.searchId = searchId;
        this.hotelId = hotelId;
        this.checkin = checkin;
        this.checkout = checkout;
        this.ages = ages;
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

    public List<Integer> getAges() {
        return ages;
    }

    public void setAges(List<Integer> ages) {
        this.ages = ages;
    }

}
