package io.codelex.flightplanner.flights.inmemory;

import io.codelex.flightplanner.flights.FlightRepository;
import io.codelex.flightplanner.flights.domain.Airport;
import io.codelex.flightplanner.flights.domain.Flight;
import io.codelex.flightplanner.flights.domain.SearchFlightRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


@Repository
@ConditionalOnProperty(prefix = "flightplanner", name = "appmode", havingValue = "inmemory")
public class FlightInMemoryRepository implements FlightRepository {

    private List<Flight> flightList = new CopyOnWriteArrayList<>();

    public List<Flight> searchFlights(SearchFlightRequest request) {
        List<Flight> list = flightList.stream().filter(flight ->
                flight.getFrom().getAirport().equals(request.getFrom()) &&
                        flight.getTo().getAirport().equals(request.getTo()) &&
                        flight.getDepartureDate().equals(request.getDepartureDate())).toList();
        return list;
    }

    public Airport searchAirports(String search) {
        String lowerCaseSearch = search.trim().toLowerCase();
        Flight ifFoundInGetToAirport = flightList.stream().filter(flight ->
                flight.getTo().getAirport().toLowerCase().contains(lowerCaseSearch) ||
                        flight.getTo().getCountry().toLowerCase().contains(lowerCaseSearch) ||
                        flight.getTo().getCity().toLowerCase().contains(lowerCaseSearch)).findFirst().orElse(null);
        Flight ifFoundInGetFromAirport = flightList.stream().filter(flight ->
                flight.getFrom().getAirport().toLowerCase().contains(lowerCaseSearch) ||
                        flight.getFrom().getCountry().toLowerCase().contains(lowerCaseSearch) ||
                        flight.getFrom().getCity().toLowerCase().contains(lowerCaseSearch)).findFirst().orElse(null);
        if (ifFoundInGetToAirport != null) {
            return ifFoundInGetToAirport.getTo();
        } else if (ifFoundInGetFromAirport != null) {
            return ifFoundInGetFromAirport.getFrom();
        }
        return null;
    }

    public void clearFlightsList() {
        flightList.clear();
    }

    public void removeFlight(long id) {
        flightList.removeIf(flight -> flight.getId() == id);
    }

    public Flight fetchFlight(long id) {
        return flightList.stream().filter(flight -> flight.getId() == id).findFirst().orElse(null);
    }

    public List<Flight> getFlightList() {
        return flightList;
    }

    public void addFlight(Flight flight) {
        flightList.add(flight);
    }


}
