package org.coolCompany.model;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class FlightScheduleTest{
    FlightSchedule schedule;
    @DataProvider(name = "crewsCases")
    public Object[][] provideCrews() {
        return new Object[][]{
            //crews
            {
                Arrays.asList(new CrewMember(0,"Pupan","Vovkin"),
                        new CrewMember(1,"Vasyan","Pupankin")
                ),
                null
            },
            {
                null,
                new IllegalArgumentException("Crews не может быть null.")
            },
            {
                Arrays.asList(),
                new IllegalArgumentException("Crews не может быть пустым.")
            },
            {
                Arrays.asList(new CrewMember(0,"Pupan","Vovkin"),
                        new CrewMember(-1,"Vasyan","Pupankin")
                ),
                new IllegalArgumentException("Crews не может быть отрицательным.")
            },
            {
                Arrays.asList(new CrewMember(0,"Pupan","Vovkin"),
                        null),
                new IllegalArgumentException("Crews не может быть null.")
            }
        };
    }
    @DataProvider(name = "flightCases")
    public Object[][] provideFlights() {
        return new Object[][]{
            //flights
            {
                Arrays.asList(new Flight("type",
                        "number",
                        "departureAirport",
                        "arrivalAirport",
                        LocalDateTime.now(),
                        LocalDateTime.now().plusHours(2),
                        Arrays.asList(1, 2, 3)),
                            new Flight("type",
                        "number",
                        "departureAirport",
                        "arrivalAirport",
                        LocalDateTime.now(),
                        LocalDateTime.now().plusHours(4),
                        Arrays.asList(4, 5, 6))
                ),
                null
            },
            {
                Arrays.asList(new Flight("type",
                        "number",
                        "departureAirport",
                        "arrivalAirport",
                        LocalDateTime.now(),
                        LocalDateTime.now().plusHours(2),
                        Arrays.asList(1, 2, 3)),
                            new Flight("type",
                        "number",
                        "departureAirport",
                        "arrivalAirport",
                        LocalDateTime.now(),
                        LocalDateTime.now().plusHours(4),
                        null)
                ),
                new IllegalArgumentException("Crews не может быть null.")
            },
            {
                Arrays.asList(new Flight("type",
                        "number",
                        "departureAirport",
                        "arrivalAirport",
                        LocalDateTime.now(),
                        LocalDateTime.now().plusHours(2),
                        Arrays.asList(1, 2, 3)),
                            new Flight("type",
                        "number",
                        "departureAirport",
                        "arrivalAirport",
                        LocalDateTime.now(),
                        LocalDateTime.now().plusHours(4),
                        Arrays.asList(4, null, 6))
                ),
                new IllegalArgumentException("Crew IDs не может быть null.")
            },
            {
                Arrays.asList(new Flight("type",
                        "number",
                        "departureAirport",
                        "arrivalAirport",
                        LocalDateTime.now(),
                        LocalDateTime.now().plusHours(2),
                        Arrays.asList(1, 2, 3)),
                            new Flight("type",
                        "number",
                        "departureAirport",
                        "arrivalAirport",
                        LocalDateTime.now(),
                        LocalDateTime.now().plusHours(4),
                        Arrays.asList(4, 5, 6))
                ),
                null
            },
            {
                Arrays.asList(new Flight("type",
                        "number",
                        "departureAirport",
                        "arrivalAirport",
                        LocalDateTime.now(),
                        LocalDateTime.now().plusHours(2),
                        Arrays.asList(1, 2, 3)),
                            new Flight("type",
                        "number",
                        "departureAirport",
                        "arrivalAirport",
                        LocalDateTime.now(),
                        LocalDateTime.now().plusHours(4),
                        Arrays.asList(4, 5, 6))
                ),
                null
            }
        };
    }
    @BeforeMethod
    public void start(){
        schedule = new FlightSchedule();
    }
    //crewmembers
    @Test(dataProvider = "crewsCases")
    public void testValidateCrewMembers(List<CrewMember> crewMembers, Exception expectedException) {
        try {
            FlightSchedule.validateCrewMembers(crewMembers);
            if (expectedException != null) {
                fail("Ожидалось исключение: " + expectedException);
            }
        } catch (Exception actualException) {
            if (expectedException == null) {
                fail("Исключение не ожидалось, но было выброшено: " + actualException);
            }
            assertEquals(actualException.getClass(), expectedException.getClass(), "Тип исключения не совпадает.");
            assertEquals(actualException.getMessage(), expectedException.getMessage(), "Сообщение исключения не совпадает.");
        }
    }

    @Test(dataProvider = "crewsCases")
    public void testIsValidCrewMembers(List<CrewMember> crewMembers, Exception expectedException) {
        assertEquals(expectedException == null, FlightSchedule.isValidCrewMembers(crewMembers));
    }

    @Test(dataProvider = "crewsCases")
    public void testSetterGetterCrewMembers(List<CrewMember> crewMembers, Exception expectedException) {
        try {
            schedule.setCrewMembers(crewMembers);
            if (expectedException != null) {
                fail("Ожидалось исключение: " + expectedException);
            }
            assertEquals(crewMembers,schedule.getCrewMembers());
        } catch (Exception actualException) {
            if (expectedException == null) {
                fail("Исключение не ожидалось, но было выброшено: " + actualException);
            }
            assertEquals(actualException.getClass(), expectedException.getClass(), "Тип исключения не совпадает.");
            assertEquals(actualException.getMessage(), expectedException.getMessage(), "Сообщение исключения не совпадает.");
        }
    }

    //Flights
    @Test(dataProvider = "flightCases")
    public void testValidateFlights(List<Flight> flights, Exception expectedException) {
        try {
            FlightSchedule.validateFlights(flights);
            if (expectedException != null) {
                fail("Ожидалось исключение: " + expectedException);
            }
        } catch (Exception actualException) {
            if (expectedException == null) {
                fail("Исключение не ожидалось, но было выброшено: " + actualException);
            }
            assertEquals(actualException.getClass(), expectedException.getClass(), "Тип исключения не совпадает.");
            assertEquals(actualException.getMessage(), expectedException.getMessage(), "Сообщение исключения не совпадает.");
        }
    }

    @Test(dataProvider = "flightCases")
    public void testIsValidFlights(List<Flight> flights, Exception expectedException) {
        assertEquals(expectedException == null, FlightSchedule.isValidFlights(flights));
    }

    @Test(dataProvider = "flightCases")
    public void testSetterGetterFlights(List<Flight> flights, Exception expectedException) {
        try {
            schedule.setFlights(flights);
            if (expectedException != null) {
                fail("Ожидалось исключение: " + expectedException);
            }
            assertEquals(flights,schedule.getFlights());
        } catch (Exception actualException) {
            if (expectedException == null) {
                fail("Исключение не ожидалось, но было выброшено: " + actualException);
            }
            assertEquals(actualException.getClass(), expectedException.getClass(), "Тип исключения не совпадает.");
            assertEquals(actualException.getMessage(), expectedException.getMessage(), "Сообщение исключения не совпадает.");
        }
    }
}