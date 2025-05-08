package org.coolCompany.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;


import java.util.List;
import java.time.LocalDateTime;
import java.lang.String;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Flight{
    private String aircraftType; // тип воздушного судна
    private String aircraftNumber; // номер воздушного судна
    private String departureAirport; // название аэропорта вылета
    private String arrivalAirport; // название аэропорта назначения
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime departureTime; // время взлета
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime arrivalTime; // время посадки
    @JsonProperty("crewIds")
    private List<Integer> crewIds; // список экипажа, выполнявшего перелет.
    @JsonCreator
    public Flight(@JsonProperty("aircraftType") String aircraftType,
                  @JsonProperty("aircraftNumber")String aircraftNumber,
                  @JsonProperty("departureAirport")String departureAirport,
                  @JsonProperty("arrivalAirport")String arrivalAirport,
                  @JsonProperty("departureTime")LocalDateTime departureTime,
                  @JsonProperty("arrivalTime")LocalDateTime arrivalTime,
                  @JsonProperty("crewIds")List<Integer> crewIds) {
        this.aircraftType = aircraftType;
        this.aircraftNumber = aircraftNumber;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        if (arrivalTime.isAfter(departureTime)){ // время посадки после взлета
            this.departureTime = departureTime;
            this.arrivalTime = arrivalTime;
        }
        else {
            throw new IllegalArgumentException("Departure time раньше или такой же, что и arrival time");
        }
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
        if (this.arrivalTime.isAfter(departureTime)){ // время посадки после взлета
            this.departureTime = departureTime;
        }
        else {
            throw new IllegalArgumentException("Departure time раньше или такой же, что и arrival time");
        }
    }
    @JsonSetter("departureTime")
    public void setDepartureTimeUnSafe(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }
    public void setArrivalTime(LocalDateTime arrivalTime) {
        if (arrivalTime.isAfter(this.departureTime)){ // время посадки после взлета
            this.arrivalTime = arrivalTime;
        }
        else {
            throw new IllegalArgumentException("Departure time раньше или такой же, что и arrival time");
        }
    }
    @JsonSetter("arrivalTime")
    public void setArrivalTimeUnSafe(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }


    public List<Integer> getCrewIds() {
        return crewIds;
    }
    public void setCrewIds(List<Integer> crewIds) {
        this.crewIds = crewIds;
    }
}
