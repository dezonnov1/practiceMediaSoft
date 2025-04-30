package org.coolCompany.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import java.util.List;
import java.time.LocalDateTime;
import java.lang.String;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Flight {
    private String aircraftType; // тип воздушного судна
    private String aircraftNumber; // номер воздушного судна
    private String departureAirport; // название аэропорта вылета
    private String arrivalAirport; // название аэропорта назначения
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime departureTime; // время взлета
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime arrivalTime; // время посадки
    private List<Integer> crewIds; // список экипажа, выполнявшего перелет.

    public Flight(String aircraftType, String aircraftNumber, String departureAirport, String arrivalAirport, LocalDateTime departureTime, LocalDateTime arrivalTime, List<Integer> crewIds) {
        this.aircraftType = aircraftType;
        this.aircraftNumber = aircraftNumber;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.crewIds = crewIds;
    }

    public Flight() {
    }

    public String getAircraftType() {
        return aircraftType;
    }
    public void setAircraftType(String aircraftType) {
        this.aircraftType = aircraftType;
    }

    public String getAircraftNumber() {
        return aircraftNumber;
    }
    public void setAircraftNumber(String aircraftNumber) {
        this.aircraftNumber = aircraftNumber;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }
    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }
    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
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

    public List<Integer> getCrewIds() {
        return crewIds;
    }
    public void setCrewIds(List<Integer> crewIds) {
        this.crewIds = crewIds;
    }
}
