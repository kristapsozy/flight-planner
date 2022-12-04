package io.codelex.flightplanner.flights.inmemory;

import io.codelex.flightplanner.flights.FlightService;
import io.codelex.flightplanner.flights.domain.Airport;
import io.codelex.flightplanner.flights.domain.Flight;
import io.codelex.flightplanner.flights.dto.AddFlightRequest;
import io.codelex.flightplanner.flights.dto.PageResult;
import io.codelex.flightplanner.flights.dto.SearchFlightRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
@ConditionalOnProperty(prefix = "flightplanner", name = "appmode", havingValue = "inmemory")
public class FlightInMemoryService implements FlightService {

    private long flightIdGeneratorBase = 100000;
    private int pageResultCounter = 0;

    private final FlightInMemoryRepository flightRepository;

    public FlightInMemoryService(FlightInMemoryRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public List<Flight> findFlights(SearchFlightRequest request) {
        return flightRepository.searchFlights(request);
    }

    @Override
    public PageResult getPageResultFromSearchResults(SearchFlightRequest request) {
        List<Flight> searchResults = flightRepository.searchFlights(request);
        if (pageResultCounter != 0) {
            pageResultCounter++;
        }
        return new PageResult(pageResultCounter, searchResults.size(), searchResults);
    }

    @Override
    public void saveFlight(Flight flight) {
        flightRepository.addFlight(flight);
    }

    @Override
    public void clearFlights() {
        flightRepository.clearFlightsList();
    }

    @Override
    public long getFlightId() {
        return ++flightIdGeneratorBase;
    }

    @Override
    public List<Flight> getAllFlights() {
        return flightRepository.getFlightList();
    }

    @Override
    public void deleteFlight(long id) {
        flightRepository.removeFlight(id);
    }

    @Override
    public Flight findFlight(long id) {
        return flightRepository.fetchFlight(id);
    }

    @Override
    public Airport findAirport(String search) {
        return flightRepository.searchAirports(search.trim());
    }

    @Override
    public boolean isFlightRequestDuplicated(AddFlightRequest request) {
        return flightRepository.getFlightList().stream().anyMatch(fl -> fl.getFrom()
                .equals(request.getFrom()) &&
                fl.getTo().equals(request.getTo()) &&
                fl.getCarrier().equals(request.getCarrier()) &&
                fl.getDepartureTime().equals(request.getDepartureTime()) &&
                fl.getArrivalTime().equals(request.getArrivalTime()));
    }

    @Override
    public boolean isDatesNotCorrect(AddFlightRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime departureTime = LocalDateTime.parse(request.getDepartureTime(), formatter);
        LocalDateTime arrivalTime = LocalDateTime.parse(request.getArrivalTime(), formatter);
        Duration duration = Duration.between(departureTime, arrivalTime);
        return arrivalTime.isBefore(departureTime) || duration.toHours() < 1 || duration.toDays() > 10;
    }

    @Override
    public boolean isAirportsTheSame(AddFlightRequest request) {
        return request.getTo().getAirport().trim().equalsIgnoreCase(request.getFrom().getAirport().trim());
    }

    @Override
    public Flight createFlightFromRequest(AddFlightRequest request) {
        return new Flight(getFlightId(),
                request.getFrom(),
                request.getTo(),
                request.getCarrier(),
                request.getDepartureTime(),
                request.getArrivalTime());
    }
}
