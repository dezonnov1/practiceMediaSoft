package org.coolCompany.parser;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.coolCompany.model.FlightSchedule;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;


public class JacksonTest {

    private URL resource;
    private String path;
    private final ObjectMapper mapper = new ObjectMapper();
    @BeforeMethod
    public void setup() throws URISyntaxException {
        // Загружаем тестовый JSON из ресурсов
        resource = getClass().getClassLoader().getResource("data.json");
        assert resource != null : "test_schedule.json не найден!";
        path = Paths.get(resource.toURI()).toString();
    }
    @Test(enabled = false)
    public void autoParse() throws IOException {
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new JavaTimeModule());
        FlightSchedule testedObject = mapper.readValue(new File(path), FlightSchedule.class);
        Assert.assertEquals(testedObject.getCrewMembers().get(0).getFirstName(),"Валерий");
    }
}
