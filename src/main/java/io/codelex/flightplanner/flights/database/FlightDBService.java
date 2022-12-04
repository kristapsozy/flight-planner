package io.codelex.flightplanner.flights.database;

import io.codelex.flightplanner.flights.FlightService;
import io.codelex.flightplanner.flights.domain.Airport;
import io.codelex.flightplanner.flights.domain.Flight;
import io.codelex.flightplanner.flights.dto.AddFlightRequest;
import io.codelex.flightplanner.flights.dto.PageResult;
import io.codelex.flightplanner.flights.dto.SearchFlightRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

@Service
@ConditionalOnProperty(prefix = "flightplanner", name = "appmode", havingValue = "database")
public class FlightDBService implements FlightService {

    private final FlightRepository flightRepository;
    private final AirportRepository airportRepository;

    public FlightDBService(FlightRepository flightRepository, AirportRepository airportRepository) {
        this.flightRepository = flightRepository;
        this.airportRepository = airportRepository;
    }

    @Override
    public List<Flight> findFlights(SearchFlightRequest request) {
        LocalDateTime searchDate = convertStringToDateTime(request.getDepartureDate());
        return flightRepository
                .findAllByFrom_AirportContainsIgnoreCaseAndTo_AirportContainsIgnoreCaseAndDepartureTimeGreaterThanEqualAndDepartureTimeLessThan
                        (request.getFrom(), request.getTo(), searchDate,
                                searchDate.plusDays(1));
    }

    @Override
    public PageResult getPageResultFromSearchResults(SearchFlightRequest request) {
        List<Flight> flightList = findFlights(request);
        return new PageResult(0, flightList.size(), flightList);
    }

    private LocalDateTime convertStringToDateTime(String date) {
        String dateAndTime = date + " 00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(dateAndTime, formatter);
    }


    @Override
    public void saveFlight(Flight flight) {
        flight.setFrom(getOrCreate(flight.getFrom()));
        flight.setTo(getOrCreate(flight.getTo()));
        flightRepository.save(flight);

    }

    public Airport getOrCreate(Airport airport) {
        Optional<Airport> maybeAirport = airportRepository.findById(airport.getAirport());
        return maybeAirport.orElseGet(() -> airportRepository.save(airport));
    }

    @Override
    public void clearFlights() {
        flightRepository.deleteAll();
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
        if (flightRepository.existsById(id)) {
            flightRepository.deleteById(id);
        }
    }

    @Override
    public Flight findFlight(long id) {
        Optional<Flight> flight = flightRepository.findById(id);
        return flight.orElse(null);
    }

    @Override
    public Airport findAirport(String text) {
        String search = text.trim();
        return airportRepository.
                findByAirportContainsIgnoreCaseOrCityContainsIgnoreCaseOrCountryContainsIgnoreCase(search,
                        search, search);
    }

    @Override
    public boolean isFlightRequestDuplicated(AddFlightRequest request) {

        ExampleMatcher flightMatcher = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withMatcher("airport_from", ignoreCase())
                .withMatcher("airport_to", ignoreCase())
                .withMatcher("carrier", ignoreCase())
                .withMatcher("departure_time", ignoreCase())
                .withMatcher("arrival_time", ignoreCase());

        Flight flight = createFlightFromRequest(request);
        return flightRepository.exists(Example.of(flight, flightMatcher));
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
        return new Flight(request.getFrom(),
                request.getTo(),
                request.getCarrier(),
                request.getDepartureTime(),
                request.getArrivalTime());
    }

}
