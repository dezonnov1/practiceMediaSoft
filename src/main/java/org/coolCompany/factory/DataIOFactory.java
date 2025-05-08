package org.coolCompany.factory;

import org.coolCompany.parser.DataParser;
import org.coolCompany.parser.JsonDataParser;
import org.coolCompany.writer.DataWriter;
import org.coolCompany.writer.JsonDataWriter;

public class DataIOFactory {

    public static DataWriter createWriter(String outputFile) {
        if (outputFile == null || outputFile.isEmpty()) {
            throw new IllegalArgumentException("Входной файл не может быть пустым.");
        }

        if (outputFile.endsWith(".json")) {
            return new JsonDataWriter();
        } else {
            throw new UnsupportedOperationException("Неподдерживаемый формат для сериализации: " + outputFile);
        }
    }

    public static DataParser createParser(String inputFile) {
        if (inputFile == null || inputFile.isEmpty()) {
            throw new IllegalArgumentException("Входной файл не может быть пустым.");
        }

        if (inputFile.endsWith(".json")) {
            return new JsonDataParser();
        } else {
            throw new UnsupportedOperationException("Неподдерживаемый формат для парсинга: " + inputFile);
        }
    }
}
