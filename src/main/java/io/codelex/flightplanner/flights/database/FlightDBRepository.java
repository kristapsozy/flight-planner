package io.codelex.flightplanner.flights.database;

import io.codelex.flightplanner.flights.FlightRepository;
import io.codelex.flightplanner.flights.domain.Airport;
import io.codelex.flightplanner.flights.domain.Flight;
import io.codelex.flightplanner.flights.dto.SearchFlightRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@ConditionalOnProperty(prefix = "flightplanner", name = "appmode", havingValue = "database")
public class FlightDBRepository implements FlightRepository {
    @Override
    public List<Flight> searchFlights(SearchFlightRequest request) {
        return null;
    }

    @Override
    public Airport searchAirports(String search) {
        return null;
    }

    @Override
    public void clearFlightsList() {

    }

    @Override
    public void removeFlight(long id) {

    }

    @Override
    public Flight fetchFlight(long id) {
        return null;
    }

    @Override
    public List<Flight> getFlightList() {
        return null;
    }

    @Override
    public void addFlight(Flight flight) {

    }
}
