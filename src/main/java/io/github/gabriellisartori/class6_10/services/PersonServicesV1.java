package io.github.gabriellisartori.class6_10.services;

import io.github.gabriellisartori.class6_10.controllers.TestLogController;
import static io.github.gabriellisartori.class6_10.mapper.ObjectMapperV1.parseListObjects;
import static io.github.gabriellisartori.class6_10.mapper.ObjectMapperV1.parseObject;

import io.github.gabriellisartori.class6_10.data.dto.v1.PersonDTOV1;
import io.github.gabriellisartori.class6_10.data.dto.v2.PersonDTOV2;
import io.github.gabriellisartori.class6_10.mapper.custom.PersonMapper;
import io.github.gabriellisartori.class6_10.model.PersonV1;
import io.github.gabriellisartori.class6_10.repository.PersonRepositoryV1;
import io.github.gabriellisartori.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServicesV1 {

    private Logger logger = LoggerFactory.getLogger(TestLogController.class);


    @Autowired
    PersonRepositoryV1 repository;

    @Autowired
    PersonMapper converter;

    public List<PersonDTOV1> findAll() {
        logger.info("Finding all people");

        return parseListObjects(repository.findAll(), PersonDTOV1.class);
    }

    public PersonDTOV1 findById(Long id) {
        logger.info("Finding one Person");

         var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        return parseObject(entity, PersonDTOV1.class);
    }

    public PersonDTOV1 create(PersonDTOV1 person) {
        logger.info("Creating one Person");

        var entity = parseObject(person, PersonV1.class);

        return parseObject(repository.save(entity), PersonDTOV1.class);
    }

    public PersonDTOV2 createV2(PersonDTOV2 person) {
        logger.info("Creating one PersonV2");

        var entity = parseObject(person, PersonV1.class);

        return converter.convertEntityToDTO(repository.save(entity));
    }

    public PersonDTOV1 update(PersonDTOV1 person) {
        logger.info("Updating one Person");

        PersonV1 entity = repository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return parseObject(repository.save(entity), PersonDTOV1.class);
    }

    public void delete(Long id) {
        logger.info("Deleting one Person");

        PersonV1 entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        repository.delete(entity);
    }
}
