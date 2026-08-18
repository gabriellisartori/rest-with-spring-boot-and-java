package io.github.gabriellisartori.class11_above.controllers;

import io.github.gabriellisartori.class11_above.controllers.docs.PersonControllerDocs;
import io.github.gabriellisartori.class11_above.data.dto.PersonDTO;
import io.github.gabriellisartori.services.PersonServices;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
//import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/person/v1")
@Tag(name = "People", description = "Endpoints for managing people")
public class PersonController implements PersonControllerDocs {

    @Autowired
    private PersonServices service;

    @GetMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
    })

    @Override
    public List<PersonDTO> findAll() {
        return service.findAll();
    }

    @GetMapping(
            value = "/{id}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE
            }
    )

    @Override
    public PersonDTO findById(@PathVariable("id") Long id) {
        // Learn about custom Json serialization and filtering in Spring Boot
        //var person = service.findById(id);
        //person.setBirthDate(new Date());
        //person.setPhoneNumber("+55 (11) 99999-9999");
        //person.setPhoneNumber("");
        //person.setLastName(null);
        //person.setSensitiveData("Teste de dado sensível");
        //return person;

        return service.findById(id);
    }

    @PostMapping(
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE
            },
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE
            }
    )

    @Override
    public ResponseEntity<PersonDTO> create(@RequestBody PersonDTO person) {
        PersonDTO createdPerson = service.create(person);

        return ResponseEntity
                .created(URI.create("/person/" + createdPerson.getId()))
                .body(createdPerson);
    }

    @PutMapping(
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE
            },
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE
            }
    )
    @Override
    public PersonDTO update(@RequestBody PersonDTO person) {
        return service.update(person);
    }

    @DeleteMapping(value = "/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
