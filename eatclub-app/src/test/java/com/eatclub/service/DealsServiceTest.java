package com.eatclub.service;

import com.eatclub.dto.DealsApiResponse;
import com.eatclub.dto.PeakDealsResponse;
import com.eatclub.model.Deal;
import com.eatclub.model.Restaurant;
import com.eatclub.model.RestaurantData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class DealsServiceTest {

    @Mock
    private RestaurantDataService restaurantDataService;

    @InjectMocks
    private DealsService dealsService;

    private RestaurantData testData;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        setupTestData();
        when(restaurantDataService.getRestaurantData()).thenReturn(testData);
    }

    private void setupTestData() {
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setObjectId("REST1");
        restaurant1.setName("Test Restaurant 1");
        restaurant1.setAddress1("123 Test St");
        restaurant1.setSuburb("Testville");
        restaurant1.setOpen("3:00pm");
        restaurant1.setClose("9:00pm");

        Deal deal1 = new Deal();
        deal1.setObjectId("DEAL1");
        deal1.setDiscount("50");
        deal1.setStart("6:00pm");
        deal1.setEnd("8:00pm");
        deal1.setDineIn("true");
        deal1.setLightning("false");
        deal1.setQtyLeft("5");

        Deal deal2 = new Deal();
        deal2.setObjectId("DEAL2");
        deal2.setDiscount("30");
        deal2.setDineIn("false");
        deal2.setLightning("true");
        deal2.setQtyLeft("3");

        restaurant1.setDeals(Arrays.asList(deal1, deal2));

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setObjectId("REST2");
        restaurant2.setName("Test Restaurant 2");
        restaurant2.setAddress1("456 Test Ave");
        restaurant2.setSuburb("Testburg");
        restaurant2.setOpen("1:00pm");
        restaurant2.setClose("11:00pm");

        Deal deal3 = new Deal();
        deal3.setObjectId("DEAL3");
        deal3.setDiscount("25");
        deal3.setStart("5:00pm");
        deal3.setEnd("7:00pm");
        deal3.setDineIn("true");
        deal3.setLightning("true");
        deal3.setQtyLeft("2");

        restaurant2.setDeals(Arrays.asList(deal3));

        testData = new RestaurantData();
        testData.setRestaurants(Arrays.asList(restaurant1, restaurant2));
    }

    @Test
    void testGetActiveDealsWithSpecificTime() {
        DealsApiResponse response = dealsService.getActiveDeals("6:30pm");
        
        assertEquals(3, response.getDeals().size());
        assertTrue(response.getDeals().stream()
            .anyMatch(deal -> "DEAL1".equals(deal.getDealObjectId())));
        assertTrue(response.getDeals().stream()
            .anyMatch(deal -> "DEAL2".equals(deal.getDealObjectId())));
        assertTrue(response.getDeals().stream()
            .anyMatch(deal -> "DEAL3".equals(deal.getDealObjectId())));
    }

    @Test
    void testGetActiveDealsNoDealsActive() {
        DealsApiResponse response = dealsService.getActiveDeals("10:00pm");
        
        assertEquals(0, response.getDeals().size());
    }

    @Test
    void testGetActiveDealsAlwaysActiveDeal() {
        DealsApiResponse response = dealsService.getActiveDeals("4:00pm");
        
        assertEquals(1, response.getDeals().size());
        assertEquals("DEAL2", response.getDeals().get(0).getDealObjectId());
    }

    @Test
    void testGetActiveDealsInvalidTime() {
        DealsApiResponse response = dealsService.getActiveDeals("invalid");
        
        assertEquals(0, response.getDeals().size());
    }

    @Test
    void testGetActiveDealsRestaurantClosed() {
        DealsApiResponse response = dealsService.getActiveDeals("12:00pm");
        
        assertEquals(0, response.getDeals().size());
    }

    @Test
    void testCalculatePeakDealsTime() {
        PeakDealsResponse response = dealsService.calculatePeakDealsTime();
        
        assertNotNull(response.getPeakTimeStart());
        assertNotNull(response.getPeakTimeEnd());
        assertFalse(response.getPeakTimeStart().isEmpty());
        assertFalse(response.getPeakTimeEnd().isEmpty());
    }

    @Test
    void testTimeFormats() {
        DealsApiResponse response12Hour = dealsService.getActiveDeals("6:30pm");
        DealsApiResponse response24Hour = dealsService.getActiveDeals("18:30");
        
        assertEquals(response12Hour.getDeals().size(), response24Hour.getDeals().size());
    }
}