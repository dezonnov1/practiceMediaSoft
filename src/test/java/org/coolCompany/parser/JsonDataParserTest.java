package org.coolCompany.parser;

import org.coolCompany.model.FlightSchedule;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
@Test(enabled = true)
public class JsonDataParserTest {
    private JsonDataParser parser;
    private FlightSchedule schedule;
    private String filename = "testData.json";

    @BeforeMethod
    public void setup() throws Exception {
        // Загружаем тестовый JSON из ресурсов
        parser = new JsonDataParser();
        File file = new File(JsonDataParserTest.class.getClassLoader().getResource(filename).toURI());
        schedule = parser.parse(file);
    }

    @Test
    public void testParseValidJson() throws Exception {
        Assert.assertNotNull(schedule);
        // количество объектов равно длине списка
        Assert.assertEquals(schedule.getCrewMembers().size(), 10);
        Assert.assertEquals(schedule.getFlights().size(), 10);

        Assert.assertEquals(schedule.getCrewMembers().get(0).getFirstName(), "Валерий");
        Assert.assertEquals(schedule.getFlights().get(0).getAircraftNumber(), "RA-73030");
    }
}