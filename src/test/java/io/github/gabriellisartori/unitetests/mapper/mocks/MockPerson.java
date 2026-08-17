package io.github.gabriellisartori.unitetests.mapper.mocks;

import io.github.gabriellisartori.class6_10.data.dto.v1.PersonDTOV1;
import io.github.gabriellisartori.class6_10.model.PersonV1;

import java.util.ArrayList;
import java.util.List;


public class MockPerson {

    public PersonV1 mockEntity() {
        return mockEntity(0);
    }
    
    public PersonDTOV1 mockDTO() {
        return mockDTO(0);
    }
    
    public List<PersonV1> mockEntityList() {
        List<PersonV1> personV1s = new ArrayList<PersonV1>();
        for (int i = 0; i < 14; i++) {
            personV1s.add(mockEntity(i));
        }
        return personV1s;
    }

    public List<PersonDTOV1> mockDTOList() {
        List<PersonDTOV1> persons = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            persons.add(mockDTO(i));
        }
        return persons;
    }
    
    public PersonV1 mockEntity(Integer number) {
        PersonV1 personV1 = new PersonV1();
        personV1.setAddress("Address Test" + number);
        personV1.setFirstName("First Name Test" + number);
        personV1.setGender(((number % 2)==0) ? "Male" : "Female");
        personV1.setId(number.longValue());
        personV1.setLastName("Last Name Test" + number);
        return personV1;
    }

    public PersonDTOV1 mockDTO(Integer number) {
        PersonDTOV1 person = new PersonDTOV1();
        person.setAddress("Address Test" + number);
        person.setFirstName("First Name Test" + number);
        person.setGender(((number % 2)==0) ? "Male" : "Female");
        person.setId(number.longValue());
        person.setLastName("Last Name Test" + number);
        return person;
    }

}