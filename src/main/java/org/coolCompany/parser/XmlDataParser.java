package org.coolCompany.parser;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.coolCompany.model.FlightSchedule;

import java.io.File;

public class XmlDataParser implements DataParser{
    @Override
    public FlightSchedule parse(File file) throws Exception {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule()); // поддержка LocalDateTime
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return xmlMapper.readValue(file, FlightSchedule.class);
    }
}
