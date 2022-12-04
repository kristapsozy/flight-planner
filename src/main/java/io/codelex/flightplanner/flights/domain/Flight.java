package io.codelex.flightplanner.flights.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
@Table(name = "flights")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flight_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "airport_from")
    private Airport from;

    @ManyToOne
    @JoinColumn(name = "airport_to")
    private Airport to;
    private String carrier;

    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;

    public Flight(long id, Airport from, Airport to, String carrier, String departureTime, String arrivalTime) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.carrier = carrier;
        this.departureTime = convertStringToDate(departureTime);
        this.arrivalTime = convertStringToDate(arrivalTime);
    }

    public Flight(Airport from, Airport to, String carrier, String departureTime, String arrivalTime) {
        this.from = from;
        this.to = to;
        this.carrier = carrier;
        this.departureTime = convertStringToDate(departureTime);
        this.arrivalTime = convertStringToDate(arrivalTime);
    }

    public Flight() {
    }

    private LocalDateTime convertStringToDate(String dateAndTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(dateAndTime, formatter);
    }

    public LocalDateTime getDepartureDate() {
        return departureTime;
    }

    public long getId() {
        return id;
    }

    public Airport getFrom() {
        return from;
    }

    public void setFrom(Airport from) {
        this.from = from;
    }

    public Airport getTo() {
        return to;
    }

    public void setTo(Airport to) {
        this.to = to;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public LocalDateTime getDepartureTime() {

        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {

        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {

        this.arrivalTime = arrivalTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return id == flight.id &&
                from.equals(flight.from) &&
                to.equals(flight.to) &&
                carrier.equals(flight.carrier) &&
                departureTime.equals(flight.departureTime) &&
                arrivalTime.equals(flight.arrivalTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, from, to, carrier, departureTime, arrivalTime);
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", from=" + from +
                ", to=" + to +
                ", carrier='" + carrier + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                '}';
    }
}

