package org.coolCompany.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CrewMember {

    private Integer id = 0;
    private String firstName = ""; // имя специалиста
    private String lastName = ""; // фамилия специалиста
    @JsonCreator
    public CrewMember(@JsonProperty("id")Integer id,
                      @JsonProperty("firstName")String firstName,
                      @JsonProperty("lastName")String lastName) throws IllegalArgumentException{
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
    }

    public CrewMember() {
    }

    public Integer getId() {
        return id;
    }

    public static boolean isValidId(Integer id){
        return id != null && id >= 0;
    }

    public static void validateId(Integer id) throws IllegalArgumentException{
        if (id == null) {
            throw new IllegalArgumentException("ID не может быть null.");
        }
        if (id < 0) {
            throw new IllegalArgumentException("ID не может быть отрицательным.");
        }
    }

    public void setId(Integer id) throws IllegalArgumentException{
        validateId(id);
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    public static void validateName(String name) throws IllegalArgumentException{
        if (name == null) {
            throw new IllegalArgumentException("First name или last name не может быть null.");
        }
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("First name или last name не может быть пустым.");
        }
    }

    public void setFirstName(String firstName) throws IllegalArgumentException{
        validateName(firstName);
        this.firstName = firstName.trim();
    }

    public String getLastName() {
        return lastName;
    }
    public void validate() throws IllegalArgumentException{
        validateId(this.id);
        validateName(this.firstName);
        validateName(this.lastName);
    }
    public void setLastName(String lastName) throws IllegalArgumentException{
        validateName(lastName);
        this.lastName = lastName.trim();
    }
    public static boolean isValid(CrewMember crew){
        return isValidId(crew.getId()) && isValidName(crew.getFirstName()) && isValidName(crew.getLastName());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CrewMember that = (CrewMember) obj;
        return Objects.equals(id, that.id) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }
}
