package org.coolCompany.model;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.*;

public class FlightTest {
    Flight flight;

    @DataProvider(name = "flightCases")
    public Object[][] provideFlight() {
        return new Object[][]{
            {
                "Civilian",
                "ar-222",
                "Moscow",
                "Saratov",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(2),
                List.of(1, 2, 3, 4)
            },
            {
                "SuperJet",
                "ar-337",
                "Moscow",
                "NewYork",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(2),
                List.of(5, 6, 7, 8)
            }
        };
    }


    @DataProvider(name = "aircraftTypeCases")
    public Object[][] provideAircraftType() {
        return new Object[][]{
            //{значение, exception}
            {
                "Civilian",
                null
            },
            {
                "",
                new IllegalArgumentException("Aircraft type не может быть пустым.")
            },
            {
                null,
                new IllegalArgumentException("Aircraft type не может быть null.")
            }
        };
    }

    @DataProvider(name = "aircraftNumberCases")
    public Object[][] provideAircraftNumber() {
        return new Object[][]{
            //aircraftNumber
            {
                "Ar-222",
                null
            },
            {
                "",
                new IllegalArgumentException("Aircraft не может быть пустым.")
            },
            {
                null,
                new IllegalArgumentException("Aircraft не может быть null.")
            }
        };
    }

    @DataProvider(name = "departureAirportCases")
    public Object[][] provideDepartureAirport() {
        return new Object[][]{
            //departureAirport
            {
                "Moscow",
                null
            },
            {
                "",
                new IllegalArgumentException("Departure airport не может быть пустым.")
            },
            {
                null,
                new IllegalArgumentException("Departure airport не может быть null.")
            }
        };
    }

    @DataProvider(name = "arrivalAirportCases")
    public Object[][] provideArrivalAirport() {
        return new Object[][]{
            //arrivalAirport
            {
                "Saratov",
                null
            },
            {
                "",
                new IllegalArgumentException("Arrival airport не может быть пустым.")
            },
            {
                null,
                new IllegalArgumentException("Arrival airport не может быть null.")
            }
        };
    }


    @DataProvider(name = "timeRangeCases")
    public Object[][] provideTimeRange() {
        return new Object[][]{
            //departureDateTime | arrivalDateTime | Exception
            {
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(2),
                null
            },
            {
                LocalDateTime.now(),
                LocalDateTime.now().minusHours(2),
                new IllegalArgumentException("Departure time после arrival time")
            },
            {
                null,
                LocalDateTime.now(),
                new IllegalArgumentException("Departure time не может быть null.")
            },
            {
                LocalDateTime.now(),
                null,
                new IllegalArgumentException("Arrival time не может быть null.")
            },
            {
                null,
                null,
                new IllegalArgumentException("Departure time и arrival time не могут быть null.")
            }
        };
    }

    @DataProvider(name = "crewIdsCases")
    public Object[][] provideCrewIds() {
        return new Object[][]{
            //crewIds
            {
                Arrays.asList(1, 2, 3, 4),
                null
            },
            {
                null,
                new IllegalArgumentException("Crew IDs не может быть null.")
            },
            {
                Arrays.asList(),
                new IllegalArgumentException("Crew IDs не может быть пустым.")
            },
            {
                Arrays.asList(1, -1, 3, 4),
                new IllegalArgumentException("ID не может быть отрицательным.")
            },
            {
                Arrays.asList(5, null, 7, 7),
                new IllegalArgumentException("ID не может быть null.")
            }
        };
    }

    @BeforeMethod
    public void start() {
        // aircraftType, aircraftNumber, departureAirport, arrivalAirport, departureTime, arrivalTime, crewIds
//        flight = new Flight("type",
//                "number",
//                "departureAirport",
//                "arrivalAirport",
//                LocalDateTime.now(),
//                LocalDateTime.now().plusHours(2),
//                Arrays.asList(1,2,3));
        flight = new Flight();
    }

    @Test(dataProvider = "aircraftTypeCases")
    public void testIsValidAircraftType(Object value, Exception expectedException) {
        assertEquals(expectedException == null, Flight.isValidAircraftType((String) value));
    }

    @Test(dataProvider = "aircraftTypeCases")
    public void testValidateAircraftType(Object value, Exception expectedException) {
        try {
            Flight.validateAircraftType((String) value);
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

    @Test(dataProvider = "aircraftTypeCases")
    public void testSetterGetterAircraftType(Object value, Exception expectedException) {
        try {
            flight.setAircraftType((String) value);
            if (expectedException != null) {
                fail("Ожидалось исключение: " + expectedException);
            }
            assertEquals(value, flight.getAircraftType(), "Значения не совпадают.");
        } catch (Exception actualException) {
            if (expectedException == null) {
                fail("Исключение не ожидалось, но было выброшено: " + actualException);
            }
            assertEquals(actualException.getClass(), expectedException.getClass(), "Тип исключения не совпадает.");
            assertEquals(actualException.getMessage(), expectedException.getMessage(), "Сообщение исключения не совпадает.");
        }
    }

    //aircraftNumber
    @Test(dataProvider = "aircraftNumberCases")
    public void testIsValidAircraftNumber(Object value, Exception expectedException) {
        assertEquals(expectedException == null, Flight.isValidAircraftNumber((String) value));
    }

    @Test(dataProvider = "aircraftNumberCases")
    public void testValidateAircraftNumber(Object value, Exception expectedException) {
        try {
            Flight.validateAircraftNumber((String) value);
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

    @Test(dataProvider = "aircraftNumberCases")
    public void testSetterGetterAircraftNumber(Object value, Exception expectedException) {
        try {
            flight.setAircraftNumber((String) value);
            if (expectedException != null) {
                fail("Ожидалось исключение: " + expectedException);
            }
            assertEquals(value, flight.getAircraftNumber(), "Значения не совпадают.");
        } catch (Exception actualException) {
            if (expectedException == null) {
                fail("Исключение не ожидалось, но было выброшено: " + actualException);
            }
            assertEquals(actualException.getClass(), expectedException.getClass(), "Тип исключения не совпадает.");
            assertEquals(actualException.getMessage(), expectedException.getMessage(), "Сообщение исключения не совпадает.");
        }
    }

    //departureAirport
    @Test(dataProvider = "departureAirportCases")
    public void testIsValidDepartureAirport(Object value, Exception expectedException) {
        assertEquals(expectedException == null, Flight.isValidDepartureAirport((String) value));
    }

    @Test(dataProvider = "departureAirportCases")
    public void testValidateDepartureAirport(Object value, Exception expectedException) {
        try {
            Flight.validateDepartureAirport((String) value);
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

    @Test(dataProvider = "departureAirportCases")
    public void testSetterGetterDepartureAirport(Object value, Exception expectedException) {
        try {
            flight.setDepartureAirport((String) value);
            if (expectedException != null) {
                fail("Ожидалось исключение: " + expectedException);
            }
            assertEquals(value, flight.getDepartureAirport(), "Значения не совпадают.");
        } catch (Exception actualException) {
            if (expectedException == null) {
                fail("Исключение не ожидалось, но было выброшено: " + actualException);
            }
            assertEquals(actualException.getClass(), expectedException.getClass(), "Тип исключения не совпадает.");
            assertEquals(actualException.getMessage(), expectedException.getMessage(), "Сообщение исключения не совпадает.");
        }
    }

    //arrivalAirport
    @Test(dataProvider = "arrivalAirportCases")
    public void testIsValidArrivalAirport(Object value, Exception expectedException) {
        assertEquals(expectedException == null, Flight.isValidArrivalAirport((String) value));
    }

    @Test(dataProvider = "arrivalAirportCases")
    public void testValidateArrivalAirport(Object value, Exception expectedException) {
        try {
            Flight.validateArrivalAirport((String) value);
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

    @Test(dataProvider = "arrivalAirportCases")
    public void testSetterGetterArrivalAirport(Object value, Exception expectedException) {
        try {
            flight.setArrivalAirport((String) value);
            if (expectedException != null) {
                fail("Ожидалось исключение: " + expectedException);
            }
            assertEquals(value, flight.getArrivalAirport(), "Значения не совпадают.");
        } catch (Exception actualException) {
            if (expectedException == null) {
                fail("Исключение не ожидалось, но было выброшено: " + actualException);
            }
            assertEquals(actualException.getClass(), expectedException.getClass(), "Тип исключения не совпадает.");
            assertEquals(actualException.getMessage(), expectedException.getMessage(), "Сообщение исключения не совпадает.");
        }
    }

    //time
    @Test(dataProvider = "timeRangeCases")
    public void testValidateTimeRange(LocalDateTime departureDateTime, LocalDateTime arrivalDateTime, Exception expectedException) {
        try {
            Flight.validateTimeRange(departureDateTime, arrivalDateTime);
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

    @Test(dataProvider = "timeRangeCases")
    public void testIsValidTimeRange(LocalDateTime departureDateTime, LocalDateTime arrivalDateTime, Exception expectedException) {
        assertEquals(expectedException == null, Flight.isValidTimeRange(departureDateTime, arrivalDateTime));
    }

    //departureDateTime непонятно как тестировать
    @Test(dataProvider = "timeRangeCases", enabled = false)
    public void testSetterGetterDepartureTime(LocalDateTime departureDateTime, LocalDateTime arrivalDateTime, Exception expectedException) {
        try {
            flight.setDepartureTime(departureDateTime);
            if (expectedException != null) {
                fail("Ожидалось исключение: " + expectedException);
            }
            assertEquals(departureDateTime, flight.getDepartureTime(), "Значения не совпадают.");
        } catch (Exception actualException) {
            if (expectedException == null) {
                fail("Исключение не ожидалось, но было выброшено: " + actualException);
            }
            assertEquals(actualException.getClass(), expectedException.getClass(), "Тип исключения не совпадает.");
            assertEquals(actualException.getMessage(), expectedException.getMessage(), "Сообщение исключения не совпадает.");
        }
    }
    //arrivalDateTime непонятно как тестировать, ведь если вставить сеттер departureDateTime, то станет
    // эквивалентом теста testSetDepartureAndArrivalTime

    @Test(dataProvider = "timeRangeCases", enabled = false)
    public void testSetArrivalTime(LocalDateTime departureDateTime, LocalDateTime arrivalDateTime, Exception expectedException) {
        try {
            flight.setArrivalTime(arrivalDateTime);
            if (expectedException != null) {
                fail("Ожидалось исключение: " + expectedException);
            }
            assertTrue(arrivalDateTime.equals(flight.getArrivalTime()), "Значения не совпадают.");
        } catch (Exception actualException) {
            if (expectedException == null) {
                fail("Исключение не ожидалось, но было выброшено: " + actualException);
            }
            assertEquals(actualException.getClass(), expectedException.getClass(), "Тип исключения не совпадает.");
            assertEquals(actualException.getMessage(), expectedException.getMessage(), "Сообщение исключения не совпадает.");
        }
    }

    @Test(dataProvider = "timeRangeCases")
    public void testSetDepartureAndArrivalTime(LocalDateTime departureDateTime, LocalDateTime arrivalDateTime, Exception expectedException) {
        try {
            flight.setDepartureAndArrivalTime(departureDateTime, arrivalDateTime);
            if (expectedException != null) {
                fail("Ожидалось исключение: " + expectedException);
            }
            assertEquals(departureDateTime, flight.getDepartureTime(), "Значения не совпадают.");
        } catch (Exception actualException) {
            if (expectedException == null) {
                fail("Исключение не ожидалось, но было выброшено: " + actualException);
            }
            assertEquals(actualException.getClass(), expectedException.getClass(), "Тип исключения не совпадает.");
            assertEquals(actualException.getMessage(), expectedException.getMessage(), "Сообщение исключения не совпадает.");
        }
    }

    //crewIds
    @Test(dataProvider = "crewIdsCases")
    public void testIsValidCrewIds(List<Integer> crewIds, Exception expectedException) {
        assertEquals(expectedException == null, Flight.isValidCrewIds(crewIds));
    }

    @Test(dataProvider = "crewIdsCases")
    public void testValidateCrewIds(List<Integer> crewIds, Exception expectedException) {
        try {
            Flight.validateCrewIds(crewIds);
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

    @Test(dataProvider = "crewIdsCases")
    public void testSetCrewIds(List<Integer> crewIds, Exception expectedException) {
        try {
            flight.setCrewIds(crewIds);
            if (expectedException != null) {
                fail("Ожидалось исключение: " + expectedException);
            }
            assertEquals(crewIds, flight.getCrewIds(), "Значения не совпадают.");
        } catch (Exception actualException) {
            if (expectedException == null) {
                fail("Исключение не ожидалось, но было выброшено: " + actualException);
            }
            assertEquals(actualException.getClass(), expectedException.getClass(), "Тип исключения не совпадает.");
            assertEquals(actualException.getMessage(), expectedException.getMessage(), "Сообщение исключения не совпадает.");
        }
    }
}