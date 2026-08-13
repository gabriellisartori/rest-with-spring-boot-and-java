package io.github.gabriellisartori.class11_above.data.dto;

// Learn about custom Json serialization and filtering in Spring Boot

//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.annotation.JsonPropertyOrder;
//import com.fasterxml.jackson.annotation.JsonFilter;
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.databind.annotation.JsonSerialize;
//import io.github.gabriellisartori.class11_above.serializer.GenderSerializer;
//import java.util.Date;

import java.io.Serializable;
import java.util.Objects;

//@JsonPropertyOrder({"id", "first_name", "last_name", "gender", "address"})
//@JsonFilter("PersonFilter")
public class PersonDTO implements Serializable {

    private static final long serialVersionID = 1L;

    private Long id;

    //@JsonProperty("first_name")
    private String firstName;

    //@JsonProperty("last_name")
    //@JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastName;

    //@JsonInclude(JsonInclude.Include.NON_EMPTY)
    //private String phoneNumber;
    //@JsonFormat(pattern = "dd/MM/yyyy")
    //private Date birthDate;

    private String address;

    //@JsonIgnore
    //@JsonSerialize(using = GenderSerializer.class)
    private String gender;

    //private String sensitiveData;

    public PersonDTO() {
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

//    public Date getBirthDate() {
//        return birthDate;
//    }
//
//    public void setBirthDate(Date birthDate) {
//        this.birthDate = birthDate;
//    }
//
//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }
//
//    public String getSensitiveData() {
//        return sensitiveData;
//    }
//
//    public void setSensitiveData(String sensitiveData) {
//        this.sensitiveData = sensitiveData;
//    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PersonDTO personDTO = (PersonDTO) o;
        return Objects.equals(getId(), personDTO.getId()) && Objects.equals(getFirstName(), personDTO.getFirstName()) && Objects.equals(getLastName(), personDTO.getLastName()) && Objects.equals(getAddress(), personDTO.getAddress()) && Objects.equals(getGender(), personDTO.getGender());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getAddress(), getGender());
    }
}
