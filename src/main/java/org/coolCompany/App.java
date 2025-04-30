package org.coolCompany;

import org.coolCompany.factory.DataIOFactory;
import org.coolCompany.model.FlightSchedule;
import org.coolCompany.parser.DataParser;
import org.coolCompany.service.WorkTimeCalculator;
import org.coolCompany.service.WorkTimeReport;
import org.coolCompany.writer.DataWriter;

import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception{
        String inputFile = args[0];
        String outputFile = args[1];

        DataParser parser = DataIOFactory.createParser(inputFile);
        FlightSchedule schedule = parser.parse(inputFile);

        WorkTimeCalculator calculator = new WorkTimeCalculator();
        Map<String, Map<String, WorkTimeReport>> report = calculator.calculateWorkTime(schedule);

        DataWriter writer = DataIOFactory.createWriter(inputFile);
        writer.write(report, outputFile);

    }
}