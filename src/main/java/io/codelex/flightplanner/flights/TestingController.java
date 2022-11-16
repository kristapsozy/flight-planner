package io.codelex.flightplanner.flights;

import io.codelex.flightplanner.flights.inmemory.FlightInMemoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/testing-api")
@RestController
public class TestingController {

    private FlightService flightService;

    public TestingController(FlightInMemoryService flightService) {
        this.flightService = flightService;
    }

    @PostMapping("/clear")
    @ResponseStatus(HttpStatus.OK)
    public void clear() {
        flightService.clearFlights();
    }
}
