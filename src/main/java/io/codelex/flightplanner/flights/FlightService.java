package io.codelex.flightplanner.flights;

import io.codelex.flightplanner.flights.domain.Airport;
import io.codelex.flightplanner.flights.domain.Flight;
import io.codelex.flightplanner.flights.dto.AddFlightRequest;
import io.codelex.flightplanner.flights.dto.PageResult;
import io.codelex.flightplanner.flights.dto.SearchFlightRequest;

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

    boolean isFlightRequestDuplicated(AddFlightRequest request);

    boolean isDatesNotCorrect(AddFlightRequest request);

    boolean isAirportsTheSame(AddFlightRequest request);

    Flight createFlightFromRequest(AddFlightRequest request);
}


