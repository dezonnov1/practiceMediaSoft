package org.coolCompany.parser;

public interface DataParser {
    org.coolCompany.model.FlightSchedule parse(String filePath) throws Exception;
}
