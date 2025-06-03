package com.eatclub.model;

import java.util.List;

public class RestaurantData {
    private List<Restaurant> restaurants;

    public RestaurantData() {}

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }
}