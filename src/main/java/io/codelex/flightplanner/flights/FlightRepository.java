package io.codelex.flightplanner.flights;

import io.codelex.flightplanner.flights.domain.Airport;
import io.codelex.flightplanner.flights.domain.Flight;
import io.codelex.flightplanner.flights.dto.SearchFlightRequest;

import java.util.List;

public interface FlightRepository {

    List<Flight> searchFlights(SearchFlightRequest request);

    Airport searchAirports(String search);

    void clearFlightsList();

    void removeFlight(long id);

    Flight fetchFlight(long id);

    List<Flight> getFlightList();

    void addFlight(Flight flight);
}
