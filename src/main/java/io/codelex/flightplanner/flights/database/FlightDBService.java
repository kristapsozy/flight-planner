package io.codelex.flightplanner.flights.database;

import io.codelex.flightplanner.flights.FlightService;
import io.codelex.flightplanner.flights.domain.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ConditionalOnProperty(prefix = "flightplanner", name = "appmode", havingValue = "database")
public class FlightDBService implements FlightService {
    @Override
    public List<Flight> findFlights(SearchFlightRequest request) {
        return null;
    }

    @Override
    public PageResult getPageResultFromSearchResults(SearchFlightRequest request) {
        return null;
    }

    @Override
    public void saveFlight(Flight flight) {

    }

    @Override
    public void clearFlights() {

    }

    @Override
    public long getFlightId() {
        return 0;
    }

    @Override
    public List<Flight> getAllFlights() {
        return null;
    }

    @Override
    public void deleteFlight(long id) {

    }

    @Override
    public Flight findFlight(long id) {
        return null;
    }

    @Override
    public Airport findAirport(String search) {
        return null;
    }

    @Override
    public boolean isFlightRequestDuplicated(AddFlightRequest request) {
        return false;
    }

    @Override
    public boolean isDatesNotCorrect(AddFlightRequest request) {
        return false;
    }

    @Override
    public boolean isAirportsTheSame(AddFlightRequest request) {
        return false;
    }

    @Override
    public Flight createFlightFromRequest(AddFlightRequest request) {
        return null;
    }
}
