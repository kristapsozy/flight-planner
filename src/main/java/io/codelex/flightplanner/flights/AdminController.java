package io.codelex.flightplanner.flights;

import io.codelex.flightplanner.flights.domain.AddFlightRequest;
import io.codelex.flightplanner.flights.domain.Flight;
import io.codelex.flightplanner.flights.inmemory.FlightInMemoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;


@RequestMapping("/admin-api")
@RestController
@Validated
public class AdminController {

    FlightService flightService;

    public AdminController(FlightInMemoryService flightService) {
        this.flightService = flightService;
    }

    @PutMapping("/flights")
    public synchronized ResponseEntity<Flight> addFlight(@Valid @RequestBody AddFlightRequest request) {
        if (flightService.isAirportsTheSame(request) || flightService.isDatesNotCorrect(request)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Enter correct data!");
        }
        if (flightService.isFlightRequestDuplicated(request)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Flight is already added!");
        }
        Flight flight = flightService.createFlightFromRequest(request);
        flightService.saveFlight(flight);
        return new ResponseEntity<>(flight, HttpStatus.CREATED);
    }

    @DeleteMapping("/flights/{id}")
    public void deleteFlight(@PathVariable long id) {
        flightService.deleteFlight(id);
    }

    @GetMapping("/flights/{id}")
    public ResponseEntity<Flight> fetchFlight(@PathVariable long id) {
        Flight flight = flightService.findFlight(id);
        if (flight != null) {
            return new ResponseEntity<>(flight, HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found!");
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleException() {
        return "Please enter valid data!";
    }
}
