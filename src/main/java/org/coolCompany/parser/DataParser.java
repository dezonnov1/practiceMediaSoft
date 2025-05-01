package org.coolCompany.parser;

import org.coolCompany.model.FlightSchedule;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public interface DataParser {
    FlightSchedule parse(File file) throws Exception;
    // получаем файл для парсинга с абсолютной дерикторией рядом с jar файлом или файлом проекта
    static File getFileFromJarDir(String fileName) throws URISyntaxException {
        String basePath = new File(DataParser.class
                .getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .toURI()).getParent();
        return new File(basePath, fileName);
    }
    static File getFileFromJarRes(String fileName, Object object) throws URISyntaxException {
        // Загружаем JSON из ресурсов переданного объекта
        URL resource = object.getClass().getClassLoader().getResource(fileName); // берем файл из ресурсов объекта
        assert resource != null : "файл не найден!";
        String path = Paths.get(resource.toURI()).toString();
        return new File(path);
    }
}
