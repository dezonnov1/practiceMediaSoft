package org.coolCompany.parser;

import org.coolCompany.model.CrewMember;
import org.coolCompany.model.Flight;
import org.coolCompany.model.FlightSchedule;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Objects;

import static org.coolCompany.AppConfig.DATE_TIME_FORMATTER;
import static org.testng.Assert.*;

@Test(enabled = true)
public class JsonDataParserTest{
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
                                LocalDateTime.of(LocalDate.of(2025,12,30), LocalTime.of(11,59)),
                                LocalDateTime.of(LocalDate.of(2025,12,30), LocalTime.of(23,59)),
                                Arrays.asList(1, 2, 3)),
                        0,
                        new CrewMember(1,"Валерий","Жмышенко"),
                        null
                },
                {
                        "/abs/path/to/nonexistent/WrongName.json",
                        1,
                        new Flight("Cargo Aircraft",
                                "RA-73031",
                                "Москва-Шереметьево",
                                "Екатеринбург",
                                LocalDateTime.of(LocalDate.of(2025,12,31), LocalTime.of(6,0)),
                                LocalDateTime.of(LocalDate.of(2025,12,31), LocalTime.of(9,30)),
                                Arrays.asList(1, 5, 6)),
                        4,
                        new CrewMember(5,"Алексей","Иванов"),
                        new IOException("File in not absolute!")
                }
        };
    }
    @Test(dataProvider = "parseCases")
    public void testJsonParse(String filePathOrResource, Integer idFlight,
                              Flight expectedFlight, Integer idCrew,
                              CrewMember expectedCrew, Exception expectedException) {

        File file;
        if (filePathOrResource.startsWith("/")) {
            // Абсолютный путь (для ошибок)
            file = new File(filePathOrResource);
        } else {
            // Путь из ресурсов
            var resource = JsonDataParserTest.class.getClassLoader().getResource(filePathOrResource);
            assertNotNull(resource, "Ресурс не найден: " + filePathOrResource);
            file = new File(resource.getPath());
        }

        try {
            schedule = new JsonDataParser().parse(file);
            Assert.assertNotNull(schedule);
            if (expectedException != null) {
                fail("Ожидалось исключение: " + expectedException);
            }
            if (expectedFlight != null && idFlight != null)
                assertEquals(schedule.getFlights().get(idFlight), expectedFlight);
            if (expectedCrew != null && idCrew != null)
                assertEquals(schedule.getCrewMembers().get(idCrew), expectedCrew);
        } catch (Exception actualException) {
            if (expectedException == null) {
                fail("Не ожидалось исключение, но получено: " + actualException);
            }
            assertEquals(actualException.getClass(), expectedException.getClass(), "Тип исключения не совпадает.");
            assertEquals(actualException.getMessage(), expectedException.getMessage(), "Сообщение исключения не совпадает.");
            assertEquals(actualException.getClass(), expectedException.getClass());
        }
    }
}