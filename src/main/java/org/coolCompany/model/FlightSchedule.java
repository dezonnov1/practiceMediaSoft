package org.coolCompany.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightSchedule {
    @JsonProperty("CrewMembers")
    private List<CrewMember> crewMembers;
    @JsonProperty("Flights")
    private List<Flight> flights;

    public FlightSchedule(List<CrewMember> crewMembers, List<Flight> flights) {
        this.crewMembers = crewMembers;
        this.flights = flights;
    }

    public FlightSchedule() {
    }
    public static void validateСrewMembers(List<CrewMember> crewMembers) throws IllegalArgumentException{
        if (crewMembers == null) {
            throw new IllegalArgumentException("Crews не может быть null.");
        }
        if (crewMembers.isEmpty()) {
            throw new IllegalArgumentException("Crews не может быть пустым.");
        }
        for (CrewMember crew : crewMembers) {
            crew.validate();
        }
    }
    public static boolean isValidСrewMembers(List<CrewMember> crewMembers) {
        if (crewMembers == null || crewMembers.isEmpty()) return false;
        for (CrewMember crew : crewMembers){
            if(!CrewMember.isValid(crew)){
                return false;
            }
        }
        return true;
    }
    public List<CrewMember> getCrewMembers() {
        return crewMembers;
    }
    public void setCrewMembers(List<CrewMember> crewMembers) throws IllegalArgumentException {
        validateСrewMembers(crewMembers);
        this.crewMembers = crewMembers;
    }
    public static boolean isValidFlights(List<Flight> flights) {
        if (flights == null || flights.isEmpty()) return false;
        for (Flight flight : flights){
            try {
                flight.validate();
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }
    public static void validateFlights(List<Flight> flights) throws IllegalArgumentException{
        if (flights == null) {
            throw new IllegalArgumentException("Crews не может быть null.");
        }
        if (flights.isEmpty()) {
            throw new IllegalArgumentException("Crews не может быть пустым.");
        }
        for (Flight flight : flights) {
            flight.validate();
        }
    }
    public List<Flight> getFlights() {
        return flights;
    }
    public void setFlights(List<Flight> flights) {
        isValidFlights(flights);
        this.flights = flights;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        FlightSchedule that = (FlightSchedule) obj;

        return Objects.equals(crewMembers, that.crewMembers) &&
                Objects.equals(flights, that.flights);
    }

    @Override
    public int hashCode() {
        return Objects.hash(crewMembers, flights);
    }
}
