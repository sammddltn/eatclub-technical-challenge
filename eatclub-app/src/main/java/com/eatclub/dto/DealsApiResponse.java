package com.eatclub.dto;

import java.util.List;

public class DealsApiResponse {
    private List<DealResponse> deals;

    public DealsApiResponse() {}

    public DealsApiResponse(List<DealResponse> deals) {
        this.deals = deals;
    }

    public List<DealResponse> getDeals() {
        return deals;
    }

    public void setDeals(List<DealResponse> deals) {
        this.deals = deals;
    }
}