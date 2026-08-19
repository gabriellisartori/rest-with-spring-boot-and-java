package io.github.gabriellisartori.class11_above.services.services;

import io.github.gabriellisartori.class11_above.controllers.PersonController;
import io.github.gabriellisartori.class11_above.data.dto.PersonDTO;
import io.github.gabriellisartori.class11_above.model.Person;
import io.github.gabriellisartori.class11_above.repository.PersonRepository;
import io.github.gabriellisartori.class6_10.controllers.TestLogController;
import io.github.gabriellisartori.exception.RequiredObjectIsNullException;
import io.github.gabriellisartori.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.github.gabriellisartori.class6_10.mapper.ObjectMapperV1.parseListObjects;
import static io.github.gabriellisartori.class6_10.mapper.ObjectMapperV1.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonServices {

    private Logger logger = LoggerFactory.getLogger(TestLogController.class);

    @Autowired
    PersonRepository repository;

    public List<PersonDTO> findAll() {
        logger.info("Finding all people");

        var personList = parseListObjects(repository.findAll(), PersonDTO.class);

       personList.forEach(this::addHateoasLink);

        return personList;
    }

    public PersonDTO findById(Long id) {
        logger.info("Finding one Person");

         var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        var dto = parseObject(entity, PersonDTO.class);

        addHateoasLink(dto);

        return dto;
    }

    public PersonDTO create(PersonDTO person) {
        if (person == null) {
            throw new RequiredObjectIsNullException("It is not allowed to persist a null object!");
        }

        logger.info("Creating one Person");

        var entity = parseObject(person, Person.class);

        var dto = parseObject(repository.save(entity), PersonDTO.class);

        addHateoasLink(dto);

        return dto;
    }

    public PersonDTO update(PersonDTO person) {
        if (person == null) {
            throw new RequiredObjectIsNullException("It is not allowed to persist a null object!");
        }

        logger.info("Updating one Person");

        Person entity = repository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var dto = parseObject(repository.save(entity), PersonDTO.class);

        addHateoasLink(dto);

        return dto;
    }

    public void delete(Long id) {
        logger.info("Deleting one Person");

        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        repository.delete(entity);
    }

    private void addHateoasLink(PersonDTO dto) {
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
        dto.add(linkTo(methodOn(PersonController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(PersonController.class).update(dto)).withRel("update").withType("PUT"));
    }
}
