package org.coolCompany.parser;

import org.coolCompany.model.CrewMember;
import org.coolCompany.model.Flight;
import org.coolCompany.model.FlightSchedule;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

import static org.testng.Assert.*;

@Test(enabled = true)
public class JsonDataParserTest{
    private JsonDataParser parser;
    private FlightSchedule schedule;

    @DataProvider(name = "parseCases")
    public Object[][] provideParsers() {
        return new Object[][]{
                //filename, idFlight, expectedFlightFromFile, idCrew, expectedCrewFromFile, Exception
                {
                        "testData.json",
                        0,
                        new Flight("Passenger Airliner",
                                "RA-73030",
                                "Оренбург-Центральный",
                                "Якутск",
                                LocalDateTime.of(LocalDate.of(2025,12,30), LocalTime.of(21,59)),
                                LocalDateTime.of(LocalDate.of(2025,12,30), LocalTime.of(23,59)),
                                Arrays.asList(1, 2, 3)),
                        0,
                        new CrewMember(1,"Валерий","Жмышенко"),
                        null
                }
        };
    }

    @BeforeMethod
    public void setup() throws Exception {
        // Загружаем тестовый JSON из ресурсов
        parser = new JsonDataParser();

    }

    @Test(dataProvider = "parseCases")
    public void testJsonParse(String filename, Integer idFlight,
                              Flight expectedFlightFromFile, Integer idCrew,
                              CrewMember expectedCrewFromFile, Exception expectedException) throws URISyntaxException {
        File file = new File(JsonDataParserTest.class.getClassLoader().getResource(filename).toURI());
        try {
            schedule = parser.parse(file);
            Assert.assertNotNull(schedule);
            if (expectedException != null) {
                fail("Ожидалось исключение: " + expectedException);
            }
            System.out.println(schedule.getFlights().get(idFlight).getDepartureTime().equals(expectedFlightFromFile.getDepartureTime()));
            System.out.println("Parsed time: "+schedule.getFlights().get(idFlight).getDepartureTime());
            System.out.println(expectedFlightFromFile.getDepartureTime());
            assertTrue(schedule.getFlights().get(idFlight).equals(expectedFlightFromFile));
            assertTrue(schedule.getCrewMembers().get(idCrew).equals(expectedCrewFromFile));
        } catch (Exception actualException) {
            if (expectedException == null) {
                fail("Исключение не ожидалось, но было выброшено: " + actualException);
            }
            assertEquals(actualException.getClass(), expectedException.getClass(), "Тип исключения не совпадает.");
            assertEquals(actualException.getMessage(), expectedException.getMessage(), "Сообщение исключения не совпадает.");
        }
    }
}