package org.coolCompany.service;

import org.coolCompany.model.FlightSchedule;
import org.coolCompany.parser.JsonDataParser;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Map;

@Test(enabled = false)
public class WorkTimeCalculatorTest{
    JsonDataParser parser = new JsonDataParser();
    FlightSchedule schedule;
    String nameTastedFile = "testData.json";

    @BeforeTest
    public void start() throws Exception {
        System.out.println("WorkTimeCalculatorTest");
        schedule = parser.parse(new File(WorkTimeCalculatorTest.class.getResource(nameTastedFile).toString()));
    }


    @Test
    public void testCalculateWorkTime() {
        Map<Integer, Map<String, WorkTimeReport>> result = WorkTimeCalculator.calculateWorkTime(schedule);
        for (Map.Entry<Integer, Map<String, WorkTimeReport>> Crews : result.entrySet()){
            for (Map.Entry<String, WorkTimeReport>report : Crews.getValue().entrySet()){
                System.out.printf("CrewID: %4d | DailyLimit: %5b | MonthlyLimit: %5b | WeeklyLimit: %5b | totalHours: %05.2f | \n",
                        Crews.getKey(),
                        report.getValue().isExceedsDailyLimit(),
                        report.getValue().isExceedsMonthlyLimit(),
                        report.getValue().isExceedsWeeklyLimit(),
                        report.getValue().getTotalHours());
            }
        }
        Assert.assertNotNull(result);
    }

    @Test
    public void testFlightsInMonth() {
        Assert.assertNotNull(WorkTimeCalculator.groupByMonthAndWeek(schedule.getFlights()));
    }
}