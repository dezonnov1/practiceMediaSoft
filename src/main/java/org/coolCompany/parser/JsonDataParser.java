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

    public JsonDataParser() {
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new JavaTimeModule());
    }

    @Override
    public FlightSchedule parse(File file) throws Exception{
        if (!file.isAbsolute()){
            throw new IOException("File in not absolute!");
        }
        try {
            return mapper.readValue(file, FlightSchedule.class);
        }
        catch (IOException e){
            throw new IOException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
