package org.coolCompany;

import org.coolCompany.factory.DataIOFactory;
import org.coolCompany.model.FlightSchedule;
import org.coolCompany.parser.DataParser;
import org.coolCompany.service.WorkTimeCalculator;
import org.coolCompany.service.WorkTimeReport;
import org.coolCompany.writer.DataWriter;

import java.io.File;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception{
        String inputFile;
        String outputFile;
        if (args.length>0) {
            if (!args[0].isEmpty()) {
                inputFile = args[0];
            } else {
                throw new IllegalArgumentException("Empty argument \"input file name\" to inputFile");
            }
            if (!args[1].isEmpty()) {
                outputFile = args[1];
            } else {
                throw new IllegalArgumentException("Empty argument \"output file name\" for outputFile");
            }
            DataParser parser = DataIOFactory.createParser(inputFile);
            FlightSchedule schedule = parser.parse(DataParser.getFileFromJarDir(inputFile));

            WorkTimeCalculator calculator = new WorkTimeCalculator();
            Map<String, Map<String, WorkTimeReport>> report = calculator.calculateWorkTime(schedule);

            // переделать writer как json parser для работы с тестами и обычным запуском
            // т.е. добавить отдельный метод который возвращает абсолютный путь до выходного файла для использования в main
            File outFile = DataParser.getFileFromJarDir(outputFile);
            DataWriter writer = DataIOFactory.createWriter(outputFile);
            writer.write(report, outputFile);
        }
        else {
            throw new IllegalArgumentException("Empty argument list");
        }
    }
}