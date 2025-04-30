package org.coolCompany.parser;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.coolCompany.model.FlightSchedule;

import java.io.File;
import java.io.IOException;


public class JsonDataParser implements DataParser{
    private final ObjectMapper mapper = new ObjectMapper();
    @Override
    public FlightSchedule parse(String filePath) throws IOException {
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new JavaTimeModule());
        try {
            return mapper.readValue(new File(filePath), FlightSchedule.class);
        }
        catch (IOException e){
            throw new IOException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
