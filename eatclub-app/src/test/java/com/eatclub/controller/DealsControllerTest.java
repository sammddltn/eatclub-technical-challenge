package com.eatclub.controller;

import com.eatclub.dto.DealResponse;
import com.eatclub.dto.DealsApiResponse;
import com.eatclub.dto.PeakDealsResponse;
import com.eatclub.service.DealsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DealsController.class)
class DealsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DealsService dealsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetDeals() throws Exception {
        DealResponse dealResponse = new DealResponse();
        dealResponse.setDealObjectId("DEAL1");
        dealResponse.setRestaurantName("Test Restaurant");
        dealResponse.setDiscount("50");

        DealsApiResponse apiResponse = new DealsApiResponse();
        apiResponse.setDeals(Arrays.asList(dealResponse));

        when(dealsService.getActiveDeals("6:00pm")).thenReturn(apiResponse);

        mockMvc.perform(get("/deals")
                .param("timeOfDay", "6:00pm"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.deals").isArray())
                .andExpect(jsonPath("$.deals[0].dealObjectId").value("DEAL1"))
                .andExpect(jsonPath("$.deals[0].restaurantName").value("Test Restaurant"))
                .andExpect(jsonPath("$.deals[0].discount").value("50"));
    }

    @Test
    void testGetDealsNoDeals() throws Exception {
        DealsApiResponse apiResponse = new DealsApiResponse();
        apiResponse.setDeals(Arrays.asList());

        when(dealsService.getActiveDeals(anyString())).thenReturn(apiResponse);

        mockMvc.perform(get("/deals")
                .param("timeOfDay", "9:00pm"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.deals").isArray())
                .andExpect(jsonPath("$.deals").isEmpty());
    }

    @Test
    void testGetDealsMissingParameter() throws Exception {
        mockMvc.perform(get("/deals"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetPeakDeals() throws Exception {
        PeakDealsResponse peakResponse = new PeakDealsResponse("6:00pm", "8:00pm");

        when(dealsService.calculatePeakDealsTime()).thenReturn(peakResponse);

        mockMvc.perform(get("/peak-deals"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.peakTimeStart").value("6:00pm"))
                .andExpect(jsonPath("$.peakTimeEnd").value("8:00pm"));
    }
}