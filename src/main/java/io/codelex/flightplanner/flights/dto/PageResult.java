package io.codelex.flightplanner.flights.dto;

import io.codelex.flightplanner.flights.domain.Flight;

import java.util.ArrayList;
import java.util.List;

public class PageResult {
    private int page;
    private int totalItems;
    private List<Flight> items;

    public PageResult(int page, int totalItems, List<Flight> resultList) {
        this.page = page;
        this.totalItems = totalItems;
        this.items = new ArrayList<>();
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public List<Flight> getItems() {
        return items;
    }

    public void setResultList(List<Flight> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "PageResult{" +
                "page=" + page +
                ", totalItems=" + totalItems +
                ", resultList=" + items +
                '}';
    }
}
