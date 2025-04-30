package org.coolCompany.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.coolCompany.model.CrewMember;
import org.coolCompany.model.Flight;
import org.coolCompany.model.FlightSchedule;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class JsonDataParser implements DataParser{
    private final ObjectMapper mapper = new ObjectMapper();
    //mapper.setDateFormat(new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm"));
    @Override
    public FlightSchedule parse(String filePath) throws Exception {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            JsonNode root = mapper.readTree(new File(filePath));
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            List<CrewMember> crewMemberList = new ArrayList<>();
            List<Flight> flightList = new ArrayList<>();

            for (JsonNode crew : root.get("CrewMembers")) {
                int id = crew.get("id").asInt();
                String firstName = crew.get("firstName").asText();
                String lastName = crew.get("lastName").asText();
                crewMemberList.add(new CrewMember(id,firstName,lastName));
            }
             // Проходим по каждому рейсу
            for (JsonNode flightNode : root.get("Flights")) {
                Flight flight = new Flight();
                List<Integer> crewIds = new ArrayList<>();
                String aircraftType = flightNode.get("aircraftType").asText();
                String aircraftNumber = flightNode.get("aircraftNumber").asText();
                String departureAirport = flightNode.get("departureAirport").asText();
                String arrivalAirport = flightNode.get("arrivalAirport").asText();
                LocalDateTime departureTime = LocalDateTime.parse(flightNode.get("departureTime").asText(), formatter);
                LocalDateTime arrivalTime = LocalDateTime.parse(flightNode.get("arrivalTime").asText(), formatter);
                for (JsonNode crewIdNode : flightNode.get("crewIds")) {
                    crewIds.add (crewIdNode.asInt());
                }
                flightList.add(new Flight(aircraftType,
                        aircraftNumber,
                        departureAirport,
                        arrivalAirport,
                        departureTime,
                        arrivalTime,
                        crewIds
                        ));
            }
            return new FlightSchedule(crewMemberList,flightList);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
