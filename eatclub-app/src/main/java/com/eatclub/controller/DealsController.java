package com.eatclub.controller;

import com.eatclub.dto.DealsApiResponse;
import com.eatclub.dto.PeakDealsResponse;
import com.eatclub.service.DealsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DealsController {

    @Autowired
    private DealsService dealsService;

    @GetMapping("/deals")
    public DealsApiResponse getDeals(@RequestParam String timeOfDay) {
        return dealsService.getActiveDeals(timeOfDay);
    }

    @GetMapping("/peak-deals")
    public PeakDealsResponse getPeakDeals() {
        return dealsService.calculatePeakDealsTime();
    }
}