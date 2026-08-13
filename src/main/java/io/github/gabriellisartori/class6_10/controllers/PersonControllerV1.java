package io.github.gabriellisartori.class6_10.controllers;

import io.github.gabriellisartori.class6_10.data.dto.v1.PersonDTOV1;
import io.github.gabriellisartori.class6_10.data.dto.v2.PersonDTOV2;
import io.github.gabriellisartori.class6_10.services.PersonServicesV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/person/old")
public class PersonControllerV1 {

    @Autowired
    private PersonServicesV1 service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PersonDTOV1> findAll() {
        return service.findAll();
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public PersonDTOV1 findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PersonDTOV1> create(@RequestBody PersonDTOV1 person) {
        PersonDTOV1 createdPerson = service.create(person);

        return ResponseEntity
                .created(URI.create("/person/" + createdPerson.getId()))
                .body(createdPerson);
    }

    @PostMapping(
            value = "/v2",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PersonDTOV2> createV2(@RequestBody PersonDTOV2 person) {
        PersonDTOV2 createdPerson = service.createV2(person);

        return ResponseEntity
                .created(URI.create("/person/" + createdPerson.getId()))
                .body(createdPerson);
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public PersonDTOV1 update(@RequestBody PersonDTOV1 person) {
        return service.update(person);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

}
