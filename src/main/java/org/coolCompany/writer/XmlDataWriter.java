package org.coolCompany.writer;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.coolCompany.service.WorkTimeReport;

import java.io.File;
import java.util.Map;

public class XmlDataWriter implements DataWriter {
    @Override
    public void write(Map<Integer, Map<String, WorkTimeReport>> report, File outputFile) throws Exception {
        // Убедимся, что директория есть
        if (!outputFile.getParentFile().exists()) {
            boolean created = outputFile.getParentFile().mkdirs();
            if (!created) {
                throw new RuntimeException("Не удалось создать директорию: " + outputFile);
            }
        }
        // Запись XML
        XmlMapper mapper = new XmlMapper();
        mapper.writeValue(outputFile, report);
    }
}
