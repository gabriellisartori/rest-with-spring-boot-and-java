package io.github.gabriellisartori.class6_above.controllers;

import io.github.gabriellisartori.class6_above.data.dto.v1.PersonDTO;
import io.github.gabriellisartori.class6_above.data.dto.v2.PersonDTOV2;
import io.github.gabriellisartori.class6_above.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonServices service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PersonDTO> findAll() {
        return service.findAll();
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public PersonDTO findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PersonDTO> create(@RequestBody PersonDTO person) {
        PersonDTO createdPerson = service.create(person);

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
    public PersonDTO update(@RequestBody PersonDTO person) {
        return service.update(person);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

}
