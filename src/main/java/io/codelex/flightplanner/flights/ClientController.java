package io.codelex.flightplanner.flights;

import io.codelex.flightplanner.flights.domain.Airport;
import io.codelex.flightplanner.flights.domain.Flight;
import io.codelex.flightplanner.flights.dto.PageResult;
import io.codelex.flightplanner.flights.dto.SearchFlightRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api")
@RestController
@Validated
public class ClientController {

    private final FlightService flightService;

    public ClientController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/airports")
    public synchronized ResponseEntity<List<Airport>> searchAirports(@RequestParam String search) {
        Airport airport = flightService.findAirport(search);
        if (airport != null) {
            List<Airport> airportList = new ArrayList<>();
            airportList.add(airport);
            return new ResponseEntity<>(airportList, HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Airport not found!");
    }

    @PostMapping("flights/search")
    public synchronized ResponseEntity<PageResult> searchFlights(@Valid @RequestBody SearchFlightRequest request) {
        if (isAirportsTheSame(request)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Enter correct data!");
        }
        PageResult result = flightService.getPageResultFromSearchResults(request);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @GetMapping("/flights/{id}")
    public ResponseEntity<Flight> findFlightById(@PathVariable long id) {
        Flight flight = flightService.findFlight(id);
        if (flight != null) {
            return new ResponseEntity<>(flight, HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found!");
    }

    public boolean isAirportsTheSame(SearchFlightRequest request) {
        return request.getTo().trim().equalsIgnoreCase(request.getFrom().trim());
    }

}
