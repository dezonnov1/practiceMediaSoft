package org.coolCompany.factory;

import org.coolCompany.parser.DataParser;
import org.coolCompany.parser.JsonDataParser;
import org.coolCompany.writer.DataWriter;
import org.coolCompany.writer.JsonDataWriter;

public class DataIOFactory {

    public static void validateFileName(String name) throws IllegalArgumentException{
        if (name == null) {
            throw new IllegalArgumentException("Имя файла не может быть null.");
        }
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя файла не может быть пустым.");
        }
    }
    public static DataWriter createWriter(String outputFile) throws UnsupportedOperationException, IllegalArgumentException{
        validateFileName(outputFile);
        if (outputFile.endsWith(".json")) {
            return new JsonDataWriter();
        } else {
            throw new UnsupportedOperationException("Неподдерживаемый формат для сериализации.");
        }
    }

    public static DataParser createParser(String inputFile) throws UnsupportedOperationException, IllegalArgumentException{
        validateFileName(inputFile);
        if (inputFile.endsWith(".json")) {
            return new JsonDataParser();
        } else {
            throw new UnsupportedOperationException("Неподдерживаемый формат для парсинга.");
        }
    }
}
