package io.codelex.flightplanner.flights.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Table(name = "airports")
public class Airport {
    @NotBlank
    private String country;
    @NotBlank
    private String city;
    @NotBlank
    @Id
    private String airport;

    public Airport(String country, String city, String airport) {
        this.country = country;
        this.city = city;
        this.airport = airport;
    }

    public Airport() {
    }

    public boolean airportInfoContains(String text) {
        return country.toLowerCase().contains(text) ||
                city.toLowerCase().contains(text) ||
                airport.toLowerCase().contains(text);
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airport airport1 = (Airport) o;
        return country.equals(airport1.country) && city.equals(airport1.city) && airport.equals(airport1.airport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, airport);
    }

    @Override
    public String toString() {
        return "Airport{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", airport='" + airport + '\'' +
                '}';
    }
}
