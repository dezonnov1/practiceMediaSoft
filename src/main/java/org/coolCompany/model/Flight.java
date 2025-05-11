package org.coolCompany.model;

import com.fasterxml.jackson.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.lang.String;
import java.util.Objects;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Flight{
    private String aircraftType; // тип воздушного судна
    private String aircraftNumber; // номер воздушного судна
    private String departureAirport; // название аэропорта вылета
    private String arrivalAirport; // название аэропорта назначения
    //@JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime departureTime; // время взлета
    //@JsonFormat(pattern = DATE_TIME_PATTERN)
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
        setAircraftType(aircraftType);
        setAircraftNumber(aircraftNumber);
        setDepartureAirport(departureAirport);
        setArrivalAirport(arrivalAirport);
        setDepartureAndArrivalTime(departureTime,arrivalTime);
        setCrewIds(crewIds);
    }

    public Flight() {
    }

    public void validate() {
        validateAircraftType(this.aircraftType);
        validateAircraftNumber(this.aircraftNumber);
        validateDepartureAirport(this.departureAirport);
        validateArrivalAirport(this.arrivalAirport);
        validateTimeRange(this.departureTime, this.arrivalTime);
        validateCrewIds(this.crewIds);
    }

    //aircraftType
    public static boolean isValidAircraftType(String aircraftType) {
        return aircraftType != null && !aircraftType.trim().isEmpty();
    }

    public static void validateAircraftType(String aircraftType) throws IllegalArgumentException{
        if (aircraftType == null) {
            throw new IllegalArgumentException("Aircraft type не может быть null.");
        }
        if (aircraftType.trim().isEmpty()) {
            throw new IllegalArgumentException("Aircraft type не может быть пустым.");
        }
    }

    public String getAircraftType() {
        return aircraftType;
    }

    public void setAircraftType(String aircraftType) throws IllegalArgumentException{
        validateAircraftType(aircraftType);
        this.aircraftType = aircraftType;
    }

    //aircraftNumber
    public static boolean isValidAircraftNumber(String aircraftNumber) {
        return aircraftNumber != null && !aircraftNumber.trim().isEmpty();
    }

    public static void validateAircraftNumber(String aircraftNumber) throws IllegalArgumentException{
        if (aircraftNumber == null) {
            throw new IllegalArgumentException("Aircraft не может быть null.");
        }
        if (aircraftNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Aircraft не может быть пустым.");
        }
    }

    public String getAircraftNumber() {
        return aircraftNumber;
    }

    public void setAircraftNumber(String aircraftNumber) throws IllegalArgumentException{
        validateAircraftNumber(aircraftNumber);
        this.aircraftNumber = aircraftNumber;
    }

    //departureAirport
    public static boolean isValidDepartureAirport(String departureAirport) {
        return departureAirport != null && !departureAirport.trim().isEmpty();
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public static void validateDepartureAirport(String departureAirport) throws IllegalArgumentException{
        if (departureAirport == null) {
            throw new IllegalArgumentException("Departure airport не может быть null.");
        }
        if (departureAirport.trim().isEmpty()) {
            throw new IllegalArgumentException("Departure airport не может быть пустым.");
        }

    }

    public void setDepartureAirport(String departureAirport) throws IllegalArgumentException{
        validateDepartureAirport(departureAirport);
        this.departureAirport = departureAirport;
    }

    //arrivalAirport
    public static boolean isValidArrivalAirport(String arrivalAirport) {
        return arrivalAirport != null && !arrivalAirport.trim().isEmpty();
    }

    public static void validateArrivalAirport(String arrivalAirport) throws IllegalArgumentException{
        if (arrivalAirport == null) {
            throw new IllegalArgumentException("Arrival airport не может быть null.");
        }
        if (arrivalAirport.trim().isEmpty()) {
            throw new IllegalArgumentException("Arrival airport не может быть пустым.");
        }
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }


    public void setArrivalAirport(String arrivalAirport) throws IllegalArgumentException{
        validateArrivalAirport(arrivalAirport);
        this.arrivalAirport = arrivalAirport;
    }

    //time
    public static boolean isValidTimeRange(LocalDateTime departure, LocalDateTime arrival){
        if (departure == null || arrival == null) return false;
        return departure.isBefore(arrival);
    }

    public static void validateTimeRange(LocalDateTime departure, LocalDateTime arrival) throws IllegalArgumentException{
        if(departure == null && arrival == null){
            throw new IllegalArgumentException("Departure time и arrival time не могут быть null.");
        }
        if (departure == null) {
            throw new IllegalArgumentException("Departure time не может быть null.");
        }
        if (arrival == null) {
            throw new IllegalArgumentException("Arrival time не может быть null.");
        }
        if (!departure.isBefore(arrival)) {
            throw new IllegalArgumentException("Departure time после arrival time");
        }
    }
    //departureTime
    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) throws IllegalArgumentException{
        validateTimeRange(departureTime, this.arrivalTime);
        this.departureTime = departureTime;
    }

    //arrivalTime
    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) throws IllegalArgumentException{
        validateTimeRange(this.departureTime, arrivalTime);
        this.arrivalTime = arrivalTime;
    }
    public void setDepartureAndArrivalTime(LocalDateTime departureTime, LocalDateTime arrivalTime) throws IllegalArgumentException{
        validateTimeRange(departureTime, arrivalTime);
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    //crewIds
    public static boolean isValidCrewIds(List<Integer> crewIds) {
        if (crewIds == null || crewIds.isEmpty()) return false;
        return crewIds.stream().allMatch(id -> id != null && id >= 0);
    }

    public static void validateCrewIds(List<Integer> crewIds) throws IllegalArgumentException{
        if (crewIds == null) {
            throw new IllegalArgumentException("Crew IDs не может быть null.");
        }
        if (crewIds.isEmpty()) {
            throw new IllegalArgumentException("Crew IDs не может быть пустым.");
        }
        for (Integer id : crewIds) {
            CrewMember.validateId(id);
        }
    }

    public List<Integer> getCrewIds() {
        return crewIds;
    }

    public void setCrewIds(List<Integer> crewIds) throws IllegalArgumentException{
        validateCrewIds(crewIds);
        this.crewIds = new ArrayList<>(crewIds);
    }
    public static boolean isValid(Flight flight){
        return isValidCrewIds(flight.getCrewIds())&&
                isValidAircraftType(flight.getAircraftType())&&
                isValidAircraftNumber(flight.getAircraftNumber())&&
                isValidDepartureAirport(flight.getDepartureAirport())&&
                isValidArrivalAirport(flight.getArrivalAirport())&&
                isValidTimeRange(flight.getDepartureTime(),flight.getArrivalTime())&&
                isValidCrewIds(flight.getCrewIds());

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Flight flight = (Flight) obj;

        return Objects.equals(aircraftType, flight.aircraftType) &&
                Objects.equals(aircraftNumber, flight.aircraftNumber) &&
                Objects.equals(departureAirport, flight.departureAirport) &&
                Objects.equals(arrivalAirport, flight.arrivalAirport) &&
                Objects.equals(departureTime, flight.departureTime) &&
                Objects.equals(arrivalTime, flight.arrivalTime) &&
                Objects.equals(crewIds, flight.crewIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aircraftType, aircraftNumber, departureAirport, arrivalAirport,
                departureTime, arrivalTime, crewIds);
    }
}
