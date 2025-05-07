package org.coolCompany.writer;

import org.coolCompany.model.FlightSchedule;
import org.coolCompany.parser.JsonDataParser;
import org.coolCompany.service.WorkTimeCalculator;
import org.coolCompany.service.WorkTimeReport;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Test(enabled = false)
public class JsonDataWriterTest {
    private JsonDataWriter writer = new JsonDataWriter();
    private JsonDataParser parser = new JsonDataParser();
    private FlightSchedule schedule;
    private Map<Integer, Map<String, WorkTimeReport>> dataMap  = new HashMap<>();
    private File fileForParse = new File(JsonDataWriterTest.class.getResource("testData.json").toString());
    private File fileForWritter = new File(JsonDataWriterTest.class.getResource("testOut.json").toString());
    @BeforeMethod
    public void start() throws Exception {
        schedule = parser.parse(fileForParse);
        dataMap = WorkTimeCalculator.calculateWorkTime(schedule);
    }

    @Test
    public void testWrite() throws Exception {
        writer.write(dataMap, fileForWritter);
    }
}