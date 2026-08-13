package io.github.gabriellisartori.class6_10.mapper.custom;

import io.github.gabriellisartori.class6_10.data.dto.v2.PersonDTOV2;
import io.github.gabriellisartori.class6_10.model.PersonV1;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PersonMapper {

    public PersonDTOV2 convertEntityToDTO (PersonV1 personV1) {
        PersonDTOV2 personDTOV2 = new PersonDTOV2();
        personDTOV2.setId(personV1.getId());
        personDTOV2.setFirstName(personV1.getFirstName());
        personDTOV2.setLastName(personV1.getLastName());
        personDTOV2.setBirthDate(new Date());
        personDTOV2.setAddress(personV1.getAddress());
        personDTOV2.setGender(personV1.getGender());

        return personDTOV2;
    }

    public PersonV1 convertDTOToEntity (PersonDTOV2 personDTOV2) {
        PersonV1 personV1 = new PersonV1();
        personV1.setId(personDTOV2.getId());
        personV1.setFirstName(personDTOV2.getFirstName());
        personV1.setLastName(personDTOV2.getLastName());
        personV1.setAddress(personDTOV2.getAddress());
        personV1.setGender(personDTOV2.getGender());
        //person.setBirthDate(personDTOV2.getBirthDate());

        return personV1;
    }
}
