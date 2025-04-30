package org.coolCompany.parser;

import org.coolCompany.model.FlightSchedule;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class JsonDataParserTest {
    private JsonDataParser parser;
    private URL resource;
    private String path;

    @BeforeMethod
    public void setup() throws URISyntaxException {
        parser = new JsonDataParser();
        // Загружаем тестовый JSON из ресурсов
        resource = getClass().getClassLoader().getResource("data.json");
        assert resource != null : "test_schedule.json не найден!";
        path = Paths.get(resource.toURI()).toString();
    }

    @Test
    public void testParseValidJson() throws Exception {
        // Парсим файл
        FlightSchedule schedule = new JsonDataParser().parse(path);

        // Проверки
        Assert.assertNotNull(schedule);
        Assert.assertEquals(schedule.getCrewMembers().size(), 10);
        Assert.assertEquals(schedule.getFlights().size(), 10);

        Assert.assertEquals(schedule.getCrewMembers().get(0).getFirstName(), "Валерий");
        Assert.assertEquals(schedule.getFlights().get(0).getAircraftNumber(), "RA-73030");
    }
}