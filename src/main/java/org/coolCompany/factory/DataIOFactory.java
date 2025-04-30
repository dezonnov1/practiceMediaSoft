package org.coolCompany.factory;

import org.coolCompany.parser.DataParser;
import org.coolCompany.parser.JsonDataParser;
import org.coolCompany.parser.XmlDataParser;
import org.coolCompany.writer.DataWriter;
import org.coolCompany.writer.JsonDataWriter;
import org.coolCompany.writer.XmlDataWriter;

public class DataIOFactory {

    public static DataWriter createWriter(String outputFile) {
        if (outputFile == null || outputFile.isEmpty()) {
            throw new IllegalArgumentException("Output file name cannot be null or empty.");
        }

        if (outputFile.endsWith(".json")) {
            return new JsonDataWriter();
        } else if (outputFile.endsWith(".xml")) {
            return new XmlDataWriter();
        } else {
            throw new UnsupportedOperationException("Unsupported file format for writing: " + outputFile);
        }
    }

    public static DataParser createParser(String inputFile) {
        if (inputFile == null || inputFile.isEmpty()) {
            throw new IllegalArgumentException("Input file name cannot be null or empty.");
        }

        if (inputFile.endsWith(".json")) {
            return new JsonDataParser();
        } else if (inputFile.endsWith(".xml")) {
            return new XmlDataParser();
        } else {
            throw new UnsupportedOperationException("Unsupported file format for parsing: " + inputFile);
        }
    }
}
