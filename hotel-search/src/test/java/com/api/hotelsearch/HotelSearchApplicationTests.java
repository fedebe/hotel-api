package com.api.hotelsearch;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.api.hotelsearch.domain.HotelSearchDAO;
import com.api.hotelsearch.repositories.HotelSearchRepository;
import com.api.hotelsearch.service.MessagesProducerService;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application.properties")
class HotelSearchApplicationTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private HotelSearchRepository repository;

    @MockBean
    private MessagesProducerService queueService;

    @Test
    public void searchHotelAndReturnOk() throws Exception {
        String json = "{\n"
                + "    \"hotelId\": \"234234dsf\",\n"
                + "    \"checkIn\": \"29/12/2023\",\n"
                + "    \"checkOut\": \"31/12/2023\",\n"
                + "    \"ages\": [3, 29, 30, 1]\n"
                + "}";

        mvc.perform(post("/search")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void searchHotelWithoutRequiredFields() throws Exception {
        String json = "{\n"
                + "    \"hotelId\": \"\",\n"
                + "    \"checkIn\": \"\",\n"
                + "    \"checkOut\": \"\",\n"
                + "    \"ages\": []\n"
                + "}";

        mvc.perform(post("/search")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.messages[*]").value(hasItems(is("Hotel Id field need to be completed."),
                        is("Check-in field need to be completed."),
                        is("Check-out field need to be completed."),
                        is("Ages need to have at least one value."),
                        is("Check-in date must be before or equal to check-out date."))));
    }

    @Test
    public void searchHotelAndCheckInMustBeFutureOrPresent() throws Exception {
        String json = "{\n"
                + "    \"hotelId\": \"234234dsf\",\n"
                + "    \"checkIn\": \"29/12/2022\",\n"
                + "    \"checkOut\": \"31/12/2023\",\n"
                + "    \"ages\": [3, 29, 30, 1]\n"
                + "}";

        mvc.perform(post("/search")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.messages[*]").value(hasItem(is("Check-in must be a date present or future."))));
    }

    @Test
    public void searchHotelAndCheckOutMustBeFutureOrPresent() throws Exception {
        String json = "{\n"
                + "    \"hotelId\": \"234234dsf\",\n"
                + "    \"checkIn\": \"29/12/2023\",\n"
                + "    \"checkOut\": \"31/12/2022\",\n"
                + "    \"ages\": [3, 29, 30, 1]\n"
                + "}";

        mvc.perform(post("/search")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.messages[*]").value(hasItem(is("Check-out must be a date present or future."))));
    }

    @Test
    public void searchHotelAndCheckInInvalidFormat() throws Exception {
        String json = "{\n"
                + "    \"hotelId\": \"234234dsf\",\n"
                + "    \"checkIn\": \"29/12/202\",\n"
                + "    \"checkOut\": \"31/12/2023\",\n"
                + "    \"ages\": [3, 29, 30, 1]\n"
                + "}";

        mvc.perform(post("/search")
                .content(json.getBytes())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.messages[0]", is("Date field must be a valid date with dd/MM/yyyy pattern.")));
    }

    @Test
    public void searchHotelAndCheck0utInvalidFormat() throws Exception {
        String json = "{\n"
                + "    \"hotelId\": \"234234dsf\",\n"
                + "    \"checkIn\": \"29/12/2023\",\n"
                + "    \"checkOut\": \"31/12/202\",\n"
                + "    \"ages\": [3, 29, 30, 1]\n"
                + "}";

        mvc.perform(post("/search")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.messages[0]", is("Date field must be a valid date with dd/MM/yyyy pattern.")));
    }

    @Test
    public void searchHotelAndCheckInMustBeBeforeOrEqualsThanCheckOut() throws Exception {
        String json = "{\n"
                + "    \"hotelId\": \"234234dsf\",\n"
                + "    \"checkIn\": \"31/12/2023\",\n"
                + "    \"checkOut\": \"29/12/2023\",\n"
                + "    \"ages\": [3, 29, 30, 1]\n"
                + "}";

        mvc.perform(post("/search")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.messages[0]", is("Check-in date must be before or equal to check-out date.")));
    }

    @Test
    public void countHotelSearch() throws Exception {
        String uuid = UUID.randomUUID().toString();

        HotelSearchDAO hotelSearch = new HotelSearchDAO(uuid, "234234dsf",
                LocalDate.parse("29/12/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                LocalDate.parse("31/12/2022", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                List.of(3, 29, 30, 1));

        when(repository.findBySearchId(uuid)).thenReturn(Optional.of(hotelSearch));
        when(repository.countByHotelIdAndCheckinAndCheckoutAndAges(any(), any(), any(), any())).thenReturn(1L);

        mvc.perform(get("/count/".concat(uuid))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.searchId", is(uuid)))
                .andExpect(jsonPath("$.search.hotelId", is(hotelSearch.getHotelId())))
                .andExpect(jsonPath("$.count", is(1)));
    }

    @Test
    public void countHotelSearchAndSeachDoesntExists() throws Exception {
        String uuid = UUID.randomUUID().toString();

        when(repository.findBySearchId(uuid)).thenReturn(Optional.empty());

        mvc.perform(get("/count/".concat(uuid))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }
}
