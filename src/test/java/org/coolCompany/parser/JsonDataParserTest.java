package org.coolCompany.parser;

import org.coolCompany.model.FlightSchedule;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class JsonDataParserTest {
    private JsonDataParser parser;
    private FlightSchedule schedule;

    @BeforeMethod
    public void setup() throws Exception {
        // Загружаем тестовый JSON из ресурсов
        parser = new JsonDataParser();
        schedule = parser.parse(DataParser.getFileFromJarRes("testData.json",JsonDataParserTest.class));
    }

    @Test
    public void testParseValidJson() throws Exception {
        Assert.assertNotNull(schedule);
        Assert.assertEquals(schedule.getCrewMembers().size(), 10);
        Assert.assertEquals(schedule.getFlights().size(), 10);

        Assert.assertEquals(schedule.getCrewMembers().get(0).getFirstName(), "Валерий");
        Assert.assertEquals(schedule.getFlights().get(0).getAircraftNumber(), "RA-73030");
    }
}