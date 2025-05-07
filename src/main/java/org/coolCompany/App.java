package org.coolCompany;

import org.coolCompany.factory.DataIOFactory;
import org.coolCompany.model.FlightSchedule;
import org.coolCompany.parser.DataParser;
import org.coolCompany.service.WorkTimeCalculator;
import org.coolCompany.service.WorkTimeReport;
import org.coolCompany.writer.DataWriter;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import static org.coolCompany.AppConfig.*;


public class App {
    public static final Locale APP_LOCALE = new Locale("ru", "RU");

    public static void main(String[] args) throws Exception{
        printConfig();
        File inputFileDir = INPUT_DIR;
        File outputFileDir = OUTPUT_DIR;

        if (!inputFileDir.exists()) {
            throw new RuntimeException("Не удалось создать директорию: " + inputFileDir);
        }
        if (!outputFileDir.exists()) {
            throw new RuntimeException("Не удалось создать директорию: " + outputFileDir);
        }
        System.out.println("Absolute path: " + outputFileDir.getAbsolutePath());
        System.out.println("input dir: " + inputFileDir.getAbsolutePath());
        System.out.println("output dir: " + outputFileDir.getAbsolutePath());

        String inputFileName;
        String outputFileName;
        File outputFile;
        File inputFile;
        if (args.length>0) {
            if (!args[0].isEmpty()) {
                inputFileName = args[0];
            } else {
                throw new IllegalArgumentException("Пустой аргумент \"input file name\"");
            }
            if (!args[1].isEmpty()) {
                outputFileName = args[1];
            } else {
                throw new IllegalArgumentException("Пустой аргумент \"output file name\"");
            }
            System.out.println("input filename and path: " + new File(inputFileDir.getAbsolutePath(),inputFileName));
            System.out.println("output filename and path: " + new File(outputFileDir.getAbsolutePath(),outputFileName));
            DataParser parser = DataIOFactory.createParser(inputFileName);


            FlightSchedule schedule = null;
            inputFile = new File(inputFileDir, inputFileName);
            if (inputFile.setReadable(true)){
                schedule = parser.parse(inputFile);
                System.out.println("Успешное чтение " + inputFile.getAbsolutePath());
            }
            else {
                System.out.println("!!! Не удалось изменить атрибут для чтения");
            }


            Map<Integer, Map<String, WorkTimeReport>> report = WorkTimeCalculator.calculateWorkTime(schedule);
            // Map<CrewMemberId, Map<MonthYear, WorkTimeReport>>
            // 1 "02.2026" ...

            DataWriter writer = DataIOFactory.createWriter(outputFileName);
            outputFile = new File(outputFileDir, outputFileName);
            if (outputFile.getParentFile().exists()) {
                boolean success = outputFile.setWritable(true);
                System.out.println("Попытка сделать файл доступным для записи: " + success);
                if (!success) {
                    System.out.println("Не удалось изменить атрибут. Пробуем команду attrib для Windows.");
                    try {
                        String command = "attrib -r \"" + outputFile.getParentFile().getAbsolutePath() + "\"";
                        Process process = Runtime.getRuntime().exec(command);
                        process.waitFor();
                        System.out.println("Атрибут 'только чтение' удалён через команду.");
                        writer.write(report,outputFile);
                    } catch (Exception e) {
                        System.err.println("Ошибка при снятии флага через attrib: " + e.getMessage());
                    }
                }
            }
            else {
                System.out.println("Файл не существует!");
            }
            //можно, чтоб по окончанию записи открывало файл в заданном приложении по умолчанию
//            if (Desktop.isDesktopSupported()) {
//                Desktop desktop = Desktop.getDesktop();
//                try {
//                    if (outputFile.exists()){
//                        desktop.open(outputFile);
//                        System.out.println("Файл открыт.");
//                    }
//                    else {
//                        System.out.println("Файл не существует.");
//                    }
//                } catch (IOException e) {
//                    System.err.println("Не удалось открыть файл: " + e.getMessage());
//                }
//            }
        }
        else {
            throw new IllegalArgumentException("Пустой список аргументов");
        }
        System.out.println("Программа успешно завершена");
    }
}