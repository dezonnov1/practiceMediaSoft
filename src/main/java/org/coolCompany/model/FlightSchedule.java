package org.coolCompany.model;

import java.util.List;

public class FlightSchedule {
    private List<CrewMember> crewMembers;
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
