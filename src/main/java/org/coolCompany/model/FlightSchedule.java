package org.coolCompany.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
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

    public List<CrewMember> getCrewMembers() {
        return crewMembers;
    }
    public void setCrewMembers(List<CrewMember> crewMembers) {
        this.crewMembers = crewMembers;
    }

    public List<Flight> getFlights() {
        return flights;
    }
    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }
}
