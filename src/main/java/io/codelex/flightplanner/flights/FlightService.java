package io.codelex.flightplanner.flights;

import io.codelex.flightplanner.flights.domain.*;

import java.util.List;

public interface FlightService {

    List<Flight> findFlights(SearchFlightRequest request);

    PageResult getPageResultFromSearchResults(SearchFlightRequest request);

    void saveFlight(Flight flight);

    void clearFlights();

    long getFlightId();

    List<Flight> getAllFlights();

    void deleteFlight(long id);

    Flight findFlight(long id);

    Airport findAirport(String search);

    public boolean isFlightRequestDuplicated(AddFlightRequest request);

    public boolean isDatesNotCorrect(AddFlightRequest request);

    public boolean isAirportsTheSame(AddFlightRequest request);

    public Flight createFlightFromRequest(AddFlightRequest request);
}


