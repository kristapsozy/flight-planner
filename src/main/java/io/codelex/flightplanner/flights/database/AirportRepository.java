package io.codelex.flightplanner.flights.database;

import io.codelex.flightplanner.flights.domain.Airport;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;

@Repository
@ConditionalOnProperty(prefix = "flightplanner", name = "appmode", havingValue = "database")
public interface AirportRepository extends JpaRepository<Airport, String> {
    Airport findByAirportContainsIgnoreCaseOrCityContainsIgnoreCaseOrCountryContainsIgnoreCase(@NotBlank String airport,
                                                                                               @NotBlank String city,
                                                                                               @NotBlank String country);

}
