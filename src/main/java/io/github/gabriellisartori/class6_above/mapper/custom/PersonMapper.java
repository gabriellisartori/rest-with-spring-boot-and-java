package io.github.gabriellisartori.class6_above.mapper.custom;

import io.github.gabriellisartori.class6_above.data.dto.v2.PersonDTOV2;
import io.github.gabriellisartori.class6_above.model.Person;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PersonMapper {

    public PersonDTOV2 convertEntityToDTO (Person person) {
        PersonDTOV2 personDTOV2 = new PersonDTOV2();
        personDTOV2.setId(person.getId());
        personDTOV2.setFirstName(person.getFirstName());
        personDTOV2.setLastName(person.getLastName());
        personDTOV2.setBirthDate(new Date());
        personDTOV2.setAddress(person.getAddress());
        personDTOV2.setGender(person.getGender());

        return personDTOV2;
    }

    public Person convertDTOToEntity (PersonDTOV2 personDTOV2) {
        Person person = new Person();
        person.setId(personDTOV2.getId());
        person.setFirstName(personDTOV2.getFirstName());
        person.setLastName(personDTOV2.getLastName());
        person.setAddress(personDTOV2.getAddress());
        person.setGender(personDTOV2.getGender());
        //person.setBirthDate(personDTOV2.getBirthDate());

        return person;
    }
}
