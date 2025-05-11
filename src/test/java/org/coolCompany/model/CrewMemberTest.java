package org.coolCompany.model;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static org.testng.Assert.*;

public class CrewMemberTest{

    CrewMember crew;

    @BeforeMethod
    public void setup(){
        crew = new CrewMember();
    }

    @DataProvider(name = "crewCases")
    public Object[][] provideCrew() {
        return new Object[][] {
                {0,"Petya", "Pupkin"},
                {1,"Vanya", "Zloykin"},
                {2,"Vanya", "Valina"},
                {3,"Galina", "Petrovna"},
        };
    }

    @DataProvider(name = "nameCases")
    public Object[][] provideNames() {
        return new Object[][] {
                {"Petya", null},                                           // корректное значение, ошибка не ожидается
                {"", new IllegalArgumentException("First name или last name не может быть пустым.")}, // пустое значение — ожидается ошибка
                {null, new IllegalArgumentException("First name или last name не может быть null.")}  // null — ожидается ошибка
        };
    }

    @DataProvider(name = "IdCases")
    public Object[][] provideId() {
        return new Object[][] {
                {0, null},                                                                  // ошибка не ожидается
                {-1, new IllegalArgumentException("ID не может быть отрицательным.")},   // ожидается ошибка
                {null, new IllegalArgumentException("ID не может быть null.")}              // ожидается ошибка
        };
    }

    @Test(dataProvider = "crewCases")
    public void testGetters(Integer id, String firstName, String lastName){
        crew = new CrewMember(id,firstName,lastName);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(id, crew.getId());
        softAssert.assertEquals(firstName, crew.getFirstName());
        softAssert.assertEquals(lastName, crew.getLastName());
        softAssert.assertAll();
    }
    // ID
    @Test(dataProvider = "IdCases", dependsOnMethods = "testGetters")
    public void testSetID(Integer id, Exception expectedException){
        try {
            crew.setId(id);
            if (expectedException != null) {
                fail("Ожидалось исключение: " + expectedException);
            }
            assertEquals(id,crew.getId(), "Значения не совпадают.");
        } catch (Exception actualException) {
            if (expectedException == null) {
                fail("Исключение не ожидалось, но было выброшено: " + actualException);
            }
            assertEquals(actualException.getClass(), expectedException.getClass(), "Тип исключения не совпадает.");
            assertEquals(actualException.getMessage(), expectedException.getMessage(), "Сообщение исключения не совпадает.");
        }
    }

    @Test(dataProvider = "IdCases")
    public void testValidateId(Integer id, Exception expectedException) {
        try {
            CrewMember.validateId(id);
            if (expectedException != null) {
                fail("Ожидалось исключение: " + expectedException);
            }
        } catch (Exception actualException) {
            if (expectedException == null) {
                fail("Исключение не ожидалось, но было выброшено: " + actualException);
            }
            assertEquals(actualException.getClass(), expectedException.getClass(), "Тип исключения не совпадает.");
            assertEquals(actualException.getMessage(), expectedException.getMessage(), "Сообщение исключения не совпадает.");
        }
    }

    @Test(dataProvider = "IdCases")
    public void testIsValidId(Integer id, Exception expectedException) {
        assertEquals(expectedException == null, CrewMember.isValidId(id));
    }

    // names
    @Test(dataProvider = "nameCases", dependsOnMethods = "testGetters")
    public void testSetLastName(String name, Exception expectedException) {
        try {
            crew.setLastName(name);
            if (expectedException != null) {
                fail("Ожидалось исключение: " + expectedException);
            }
            assertEquals(name,crew.getLastName(), "Значения не совпадают.");
        } catch (Exception actualException) {
            if (expectedException == null) {
                fail("Исключение не ожидалось, но было выброшено: " + actualException);
            }
            assertEquals(actualException.getClass(), expectedException.getClass(), "Тип исключения не совпадает.");
            assertEquals(actualException.getMessage(), expectedException.getMessage(), "Сообщение исключения не совпадает.");
        }
    }

    @Test(dataProvider = "nameCases", dependsOnMethods = "testGetters")
    public void testSetFirstName(String name, Exception expectedException) {
        try {
            crew.setFirstName(name);
            if (expectedException != null) {
                fail("Ожидалось исключение: " + expectedException);
            }
            assertEquals(name,crew.getFirstName(), "Значения не совпадают.");
        } catch (Exception actualException) {
            if (expectedException == null) {
                fail("Исключение не ожидалось, но было выброшено: " + actualException);
            }
            assertEquals(actualException.getClass(), expectedException.getClass(), "Тип исключения не совпадает.");
            assertEquals(actualException.getMessage(), expectedException.getMessage(), "Сообщение исключения не совпадает.");
        }
    }

    @Test(dataProvider = "nameCases")
    public void testIsValidNameWith(String name, Exception expectedException) {
        assertEquals(expectedException == null, CrewMember.isValidName(name));
    }

    @Test(dataProvider = "nameCases")
    public void testValidateName(String name, Exception expectedException) {
        try {
            CrewMember.validateName(name);
            if (expectedException != null) {
                fail("Ожидалось исключение: " + expectedException);
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
