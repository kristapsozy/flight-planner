package io.codelex.flightplanner.flights.database;

import io.codelex.flightplanner.flights.domain.Flight;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@ConditionalOnProperty(prefix = "flightplanner", name = "appmode", havingValue = "database")
public interface FlightRepository extends JpaRepository<Flight, Long> {

    List<Flight> findAllByFrom_AirportContainsIgnoreCaseAndTo_AirportContainsIgnoreCaseAndDepartureTimeGreaterThanEqualAndDepartureTimeLessThan
            (@NotBlank String from_airport,
             @NotBlank String to_airport,
             @NotBlank LocalDateTime departureDate,
             @NotBlank LocalDateTime departureDatePlusOneDay);

}
