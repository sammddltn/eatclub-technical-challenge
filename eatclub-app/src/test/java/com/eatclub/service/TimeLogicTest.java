package com.eatclub.service;

import com.eatclub.dto.DealsApiResponse;
import com.eatclub.model.Deal;
import com.eatclub.model.Restaurant;
import com.eatclub.model.RestaurantData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TimeLogicTest {

    @Mock
    private RestaurantDataService restaurantDataService;

    @InjectMocks
    private DealsService dealsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testTimeParsingVariousFormats() {
        setupRestaurantWithDeals("3:00pm", "9:00pm", "6:00pm", "8:00pm");

        assertEquals(1, dealsService.getActiveDeals("6:00pm").getDeals().size());
        assertEquals(1, dealsService.getActiveDeals("18:00").getDeals().size());
        assertEquals(1, dealsService.getActiveDeals("6:30pm").getDeals().size());
        assertEquals(1, dealsService.getActiveDeals("18:30").getDeals().size());
        assertEquals(0, dealsService.getActiveDeals("5:00pm").getDeals().size());
        assertEquals(0, dealsService.getActiveDeals("9:00pm").getDeals().size());
    }

    @Test
    void testOvernightTimeRange() {
        setupRestaurantWithDeals("10:00pm", "2:00am", "11:00pm", "1:00am");

        assertEquals(1, dealsService.getActiveDeals("11:30pm").getDeals().size());
        assertEquals(1, dealsService.getActiveDeals("12:30am").getDeals().size());
        assertEquals(0, dealsService.getActiveDeals("9:00pm").getDeals().size());
        assertEquals(0, dealsService.getActiveDeals("2:00am").getDeals().size());
    }

    @Test
    void testAlwaysActiveDeal() {
        setupRestaurantWithDeals("12:00pm", "11:00pm", null, null);

        assertEquals(1, dealsService.getActiveDeals("1:00pm").getDeals().size());
        assertEquals(1, dealsService.getActiveDeals("6:00pm").getDeals().size());
        assertEquals(1, dealsService.getActiveDeals("10:00pm").getDeals().size());
        assertEquals(0, dealsService.getActiveDeals("11:30pm").getDeals().size());
    }

    @Test
    void testInvalidTimeFormats() {
        setupRestaurantWithDeals("12:00pm", "11:00pm", "6:00pm", "8:00pm");

        assertEquals(0, dealsService.getActiveDeals("invalid").getDeals().size());
        assertEquals(0, dealsService.getActiveDeals("25:00").getDeals().size());
        assertEquals(0, dealsService.getActiveDeals("13:70pm").getDeals().size());
        assertEquals(0, dealsService.getActiveDeals("").getDeals().size());
        assertEquals(0, dealsService.getActiveDeals(null).getDeals().size());
    }

    @Test
    void testRestaurantClosedOverride() {
        setupRestaurantWithDeals("6:00pm", "9:00pm", null, null);

        assertEquals(0, dealsService.getActiveDeals("5:00pm").getDeals().size());
        assertEquals(1, dealsService.getActiveDeals("7:00pm").getDeals().size());
        assertEquals(0, dealsService.getActiveDeals("10:00pm").getDeals().size());
    }

    @Test
    void testEdgeCaseTimes() {
        setupRestaurantWithDeals("12:00am", "11:59pm", "6:00pm", "8:00pm");

        assertEquals(0, dealsService.getActiveDeals("12:00am").getDeals().size());
        assertEquals(1, dealsService.getActiveDeals("7:00pm").getDeals().size());
        assertEquals(0, dealsService.getActiveDeals("11:59pm").getDeals().size());
    }

    private void setupRestaurantWithDeals(String restaurantOpen, String restaurantClose, 
                                         String dealStart, String dealEnd) {
        Restaurant restaurant = new Restaurant();
        restaurant.setObjectId("TEST_REST");
        restaurant.setName("Test Restaurant");
        restaurant.setAddress1("123 Test St");
        restaurant.setSuburb("Testville");
        restaurant.setOpen(restaurantOpen);
        restaurant.setClose(restaurantClose);

        Deal deal = new Deal();
        deal.setObjectId("TEST_DEAL");
        deal.setDiscount("50");
        deal.setStart(dealStart);
        deal.setEnd(dealEnd);
        deal.setDineIn("true");
        deal.setLightning("false");
        deal.setQtyLeft("5");

        restaurant.setDeals(Arrays.asList(deal));

        RestaurantData testData = new RestaurantData();
        testData.setRestaurants(Arrays.asList(restaurant));

        when(restaurantDataService.getRestaurantData()).thenReturn(testData);
    }
}