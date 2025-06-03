package com.eatclub.service;

import com.eatclub.model.RestaurantData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiRestaurantDataService implements RestaurantDataService {

    private final RestTemplate restTemplate;
    private final String apiUrl;

    public ApiRestaurantDataService(RestTemplate restTemplate, 
                                   @Value("${restaurant.api.url:https://eccdn.com.au/misc/challengedata.json}") String apiUrl) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
    }

    @Override
    public RestaurantData getRestaurantData() {
        try {
            return restTemplate.getForObject(apiUrl, RestaurantData.class);
        } catch (Exception e) {
            System.err.println("Failed to fetch restaurant data from API: " + e.getMessage());
            return new RestaurantData();
        }
    }
}