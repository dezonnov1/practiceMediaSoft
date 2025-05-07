package org.coolCompany.writer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.coolCompany.service.WorkTimeReport;

import java.io.File;
import java.util.Map;


public class JsonDataWriter implements DataWriter {
    @Override
    public void write(Map<Integer, Map<String, WorkTimeReport>> report, File outputFile) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        if (!outputFile.getParentFile().exists()) {
            boolean created = outputFile.mkdirs();
            if (!created) {
                throw new RuntimeException("Не удалось создать директорию: " + outputFile);
            }
        }
        // Запись JSON
        mapper.writeValue(outputFile, report);
    }
}
