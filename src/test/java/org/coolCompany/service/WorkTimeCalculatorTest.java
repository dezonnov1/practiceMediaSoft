package org.coolCompany.service;

import org.coolCompany.model.FlightSchedule;
import org.coolCompany.parser.JsonDataParser;
import org.coolCompany.parser.JsonDataParserTest;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

@Test(enabled = true)
public class WorkTimeCalculatorTest{
    FlightSchedule schedule;
    String nameTastedFile = "calculatorData.json";
    Map<Integer, Map<String, WorkTimeReport>> result;
    @DataProvider(name = "calculatorDataCases")
    public Object[][] provideCalculatorData() {
        return new Object[][]{
            //crewsid, monthYears, workReports(hours,exceedsMonthlyLimit, exceedsWeeklyLimit, exceedsDailyLimit)
            {
                Arrays.asList(1),
                "01.2025",
                new WorkTimeReport((double) 17,false,false,true)
            },
            {
                Arrays.asList(2),
                "02.2025",
                new WorkTimeReport((double) 40,false,true,false)
            },
            {
                Arrays.asList(3),
                "03.2025",
                new WorkTimeReport((double) 88,true,true,false)
            }
        };
    }
    @BeforeTest
    public void start() throws Exception {
        var resource = JsonDataParserTest.class.getClassLoader().getResource(nameTastedFile);
        File file = new File(resource.getPath());
        schedule = new JsonDataParser().parse(file);
        result = WorkTimeCalculator.calculateWorkTime(schedule);
//        System.out.println("BeforeTest calculated");
//        for (Map.Entry<Integer, Map<String, WorkTimeReport>> Crews : result.entrySet()){
//            for (Map.Entry<String, WorkTimeReport> report : Crews.getValue().entrySet()){
//                System.out.printf("CrewID: %4d| %s | DailyLimit: %5b | MonthlyLimit: %5b | WeeklyLimit: %5b | totalHours: %05.2f | \n",
//                        Crews.getKey(),
//                        report.getKey(),
//                        report.getValue().isExceedsDailyLimit(),
//                        report.getValue().isExceedsMonthlyLimit(),
//                        report.getValue().isExceedsWeeklyLimit(),
//                        report.getValue().getTotalHours());
//            }
//        }
    }

    @Test(dataProvider = "calculatorDataCases")
    public void testCalculateWorkTime(List<Integer> ids, String monthYear, WorkTimeReport report) {
        for (Integer id : ids){
            assertEquals(report, result.get(id).get(monthYear));
        }
    }

    @Test
    public void testFlightsInMonth() {
        Assert.assertNotNull(WorkTimeCalculator.groupByMonthAndWeek(schedule.getFlights()));
    }
}