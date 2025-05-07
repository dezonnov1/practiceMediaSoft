package org.coolCompany.parser;

import org.coolCompany.model.FlightSchedule;

import java.io.File;


public interface DataParser{
    FlightSchedule parse(File file) throws Exception;
}
