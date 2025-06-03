package com.eatclub.service;

import com.eatclub.dto.DealResponse;
import com.eatclub.dto.DealsApiResponse;
import com.eatclub.dto.PeakDealsResponse;
import com.eatclub.model.Deal;
import com.eatclub.model.Restaurant;
import com.eatclub.model.RestaurantData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DealsService {

    @Autowired
    private RestaurantDataService restaurantDataService;

    public DealsApiResponse getActiveDeals(String timeOfDay) {
        List<DealResponse> activeDeals = new ArrayList<>();
        RestaurantData data = restaurantDataService.getRestaurantData();

        LocalTime queryTime = parseTime(timeOfDay);
        if (queryTime == null) {
            return new DealsApiResponse(activeDeals);
        }

        for (Restaurant restaurant : data.getRestaurants()) {
            if (isRestaurantOpen(restaurant, queryTime)) {
                for (Deal deal : restaurant.getDeals()) {
                    if (isDealActive(deal, queryTime)) {
                        DealResponse dealResponse = createDealResponse(restaurant, deal);
                        activeDeals.add(dealResponse);
                    }
                }
            }
        }

        return new DealsApiResponse(activeDeals);
    }

    private LocalTime parseTime(String timeStr) {
        if (timeStr == null) return null;
        
        try {
            if (timeStr.toLowerCase().contains("am") || timeStr.toLowerCase().contains("pm")) {
                String cleanTime = timeStr.toUpperCase().replace(" ", "");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mma");
                return LocalTime.parse(cleanTime, formatter);
            } else {
                return LocalTime.parse(timeStr);
            }
        } catch (DateTimeParseException e) {
            System.err.println("Failed to parse time: " + timeStr + " - " + e.getMessage());
            return null;
        }
    }

    private boolean isTimeInRange(LocalTime queryTime, LocalTime startTime, LocalTime endTime) {
        if (startTime == null || endTime == null) {
            return false;
        }

        if (endTime.isBefore(startTime)) {
            return queryTime.isAfter(startTime) || queryTime.isBefore(endTime);
        } else {
            return !queryTime.isBefore(startTime) && queryTime.isBefore(endTime);
        }
    }

    private boolean isRestaurantOpen(Restaurant restaurant, LocalTime queryTime) {
        LocalTime openTime = parseTime(restaurant.getOpen());
        LocalTime closeTime = parseTime(restaurant.getClose());
        return isTimeInRange(queryTime, openTime, closeTime);
    }

    private boolean isDealActive(Deal deal, LocalTime queryTime) {
        String startTimeStr = deal.getStart() != null ? deal.getStart() : deal.getOpen();
        String endTimeStr = deal.getEnd() != null ? deal.getEnd() : deal.getClose();
        
        LocalTime startTime = parseTime(startTimeStr);
        LocalTime endTime = parseTime(endTimeStr);
        
        return isTimeInRange(queryTime, startTime, endTime);
    }

    private DealResponse createDealResponse(Restaurant restaurant, Deal deal) {
        DealResponse response = new DealResponse();
        response.setRestaurantObjectId(restaurant.getObjectId());
        response.setRestaurantName(restaurant.getName());
        response.setRestaurantAddress1(restaurant.getAddress1());
        response.setRestarantSuburb(restaurant.getSuburb());
        response.setRestaurantOpen(restaurant.getOpen());
        response.setRestaurantClose(restaurant.getClose());
        response.setDealObjectId(deal.getObjectId());
        response.setDiscount(deal.getDiscount());
        response.setDineIn(deal.getDineIn());
        response.setLightning(deal.getLightning());
        response.setQtyLeft(deal.getQtyLeft());
        return response;
    }

    public PeakDealsResponse calculatePeakDealsTime() {
        int maxDeals = 0;
        LocalTime peakStart = null;
        LocalTime peakEnd = null;
        
        LocalTime currentStart = null;
        int currentDeals = 0;
        
        for (int hour = 10; hour <= 23; hour++) {
            for (int minute = 0; minute < 60; minute += 15) {
                LocalTime time = LocalTime.of(hour, minute);
                String timeStr = formatTimeForQuery(time);
                
                DealsApiResponse response = getActiveDeals(timeStr);
                int dealCount = response.getDeals().size();
                
                if (dealCount > currentDeals) {
                    currentStart = time;
                    currentDeals = dealCount;
                    maxDeals = dealCount;
                } else if (dealCount < currentDeals) {
                    if (currentDeals == maxDeals && currentStart != null) {
                        peakStart = currentStart;
                        peakEnd = time;
                    }
                    currentDeals = dealCount;
                }
            }
        }
        
        if (peakStart == null && currentDeals == maxDeals && currentStart != null) {
            peakStart = currentStart;
            peakEnd = LocalTime.of(23, 45);
        }
        
        if (peakStart == null) {
            return new PeakDealsResponse("12:00am", "12:00am");
        }
        
        return new PeakDealsResponse(
            formatTimeForResponse(peakStart),
            formatTimeForResponse(peakEnd)
        );
    }
    
    private String formatTimeForQuery(LocalTime time) {
        return time.format(DateTimeFormatter.ofPattern("H:mm"));
    }
    
    private String formatTimeForResponse(LocalTime time) {
        return time.format(DateTimeFormatter.ofPattern("h:mma")).toLowerCase();
    }
}