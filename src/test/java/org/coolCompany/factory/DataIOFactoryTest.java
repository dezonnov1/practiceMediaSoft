package org.coolCompany.factory;

import org.coolCompany.parser.DataParser;
import org.coolCompany.parser.JsonDataParser;
import org.coolCompany.writer.DataWriter;
import org.coolCompany.writer.JsonDataWriter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Objects;

import static org.testng.Assert.*;

public class DataIOFactoryTest{

    @DataProvider(name = "fileNameCases")
    public Object[][] provideFileNames() {
        return new Object[][] {
                //[функция] | входящие данные | ожидается на выходе | ожидаемая ошибка
                {"parser","input.json", new JsonDataParser(), null},
                {"parser", "input.228337", null, new UnsupportedOperationException("Неподдерживаемый формат для парсинга.") },
                {"parser", "", null, new IllegalArgumentException("Имя файла не может быть пустым.") },
                {"parser", null, null, new IllegalArgumentException("Имя файла не может быть null.")},

                {"writer", "output.json", new JsonDataWriter(), null},
                {"writer", "output.228337", null, new UnsupportedOperationException("Неподдерживаемый формат для сериализации.")},
                {"writer", "", null, new IllegalArgumentException("Имя файла не может быть пустым.")},
                {"writer", null, null, new IllegalArgumentException("Имя файла не может быть null.")},

                {"validator", "file.json", null, null},
                {"validator", "", null, new IllegalArgumentException("Имя файла не может быть пустым.")},
                {"validator", null, null, new IllegalArgumentException("Имя файла не может быть null.")},
        };
    }

    @Test(dataProvider = "fileNameCases")
    public void testCreateWrite(String func, Object inputValue, Object outputValue, Exception expectedException) {
        if (Objects.equals(func, "writer")){
            try {
                DataWriter actualValue = DataIOFactory.createWriter((String) inputValue);
                if (expectedException != null) {
                    fail("Ожидалось исключение: " + expectedException.toString());
                }
                assertEquals(actualValue.getClass(),outputValue.getClass() , "Значения не совпадают.");
            } catch (Exception actualException) {
                if (expectedException == null) {
                    fail("Исключение не ожидалось, но было выброшено: " + actualException);
                }
                assertEquals(actualException.getClass(), expectedException.getClass(), "Тип исключения не совпадает.");
                assertEquals(actualException.getMessage(), expectedException.getMessage(), "Сообщение исключения не совпадает.");
            }
        }
    }


    @Test(dataProvider = "fileNameCases")
    public void testCreateParser(String func, Object inputValue, Object outputValue, Exception expectedException) {
        if (Objects.equals(func, "parser")){
            try {
                Object actualValue = DataIOFactory.createParser((String) inputValue);
                if (expectedException != null) {
                    fail("Ожидалось исключение: " + expectedException.toString());
                }
                assertEquals(actualValue.getClass(),outputValue.getClass() , "Значения не совпадают.");
            } catch (Exception actualException) {
                if (expectedException == null) {
                    fail("Исключение не ожидалось, но было выброшено: " + actualException);
                }
                assertEquals(actualException.getClass(), expectedException.getClass(), "Тип исключения не совпадает.");
                assertEquals(actualException.getMessage(), expectedException.getMessage(), "Сообщение исключения не совпадает.");
            }
        }
    }

    @Test(dataProvider = "fileNameCases")
    public void testValidateFileName(String func, Object inputValue, Object outputValue, Exception expectedException) {
        if (Objects.equals(func, "validator")){
            try {
                DataIOFactory.validateFileName((String) inputValue);
                if (expectedException != null) {
                    fail("Ожидалось исключение: " + expectedException.toString());
                }
            } catch (Exception actualException) {
                if (expectedException == null) {
                    fail("Исключение не ожидалось, но было выброшено: " + actualException);
                }
                assertEquals(actualException.getClass(), expectedException.getClass(), "Тип исключения не совпадает.");
                assertEquals(actualException.getMessage(), expectedException.getMessage(), "Сообщение исключения не совпадает.");
            }
        }
    }
}
