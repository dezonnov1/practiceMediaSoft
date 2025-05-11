package org.coolCompany.writer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.coolCompany.model.FlightSchedule;
import org.coolCompany.parser.JsonDataParser;
import org.coolCompany.service.WorkTimeReport;
import org.coolCompany.service.WorkTimeCalculator;
import org.testng.annotations.Test;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.Map;

public class JsonDataWriterTest {

    private JsonDataWriter writer = new JsonDataWriter();
    private JsonDataParser parser = new JsonDataParser();

    @Test
    public void testJsonDataWriterProducesCorrectOutput() throws Exception {
        // Загрузка входных данных
        URL inputUrl = JsonDataWriterTest.class.getClassLoader().getResource("testWriterInput.json");
        if (inputUrl == null) {
            throw new IllegalStateException("Input file not found in resources");
        }
        File inputFile = new File(inputUrl.toURI());


        URL expectedUrl = JsonDataWriterTest.class.getClassLoader().getResource("testWriterOutput.json");
        if (expectedUrl == null) {
            throw new IllegalStateException("Output file not found in resources");
        }
        File expectedFile = new File(expectedUrl.toURI());

        // Парсинг и вычисление
        FlightSchedule schedule = parser.parse(inputFile);
        Map<Integer, Map<String, WorkTimeReport>> dataMap = WorkTimeCalculator.calculateWorkTime(schedule);

        // Запись во временный файл
        File tempOutputFile = File.createTempFile("test-output", ".json");
        writer.write(dataMap, tempOutputFile);

        // Сравнение с ожидаемым выводом
        String expectedJson = Files.readString(expectedFile.toPath());
        String actualJson = Files.readString(tempOutputFile.toPath());

        ObjectMapper mapper = new ObjectMapper();
        Object expectedObj = mapper.readTree(expectedJson);
        Object actualObj = mapper.readTree(actualJson);

        assert expectedObj.equals(actualObj) : "Output JSON не совпадает с ожидаемым";
    }
}
