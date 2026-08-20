package io.github.gabriellisartori.integrationtests.dto.wrappers.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.gabriellisartori.integrationtests.dto.PersonDTO;

import java.io.Serializable;
import java.util.List;

public class PersonEmbeddedDTO implements Serializable {

    private static final long serialVersionID = 1L;

    @JsonProperty("people")
    private List<PersonDTO> people;

    public PersonEmbeddedDTO() {
    }

    public List<PersonDTO> getPeople() {
        return people;
    }

    public void setPeople(List<PersonDTO> people) {
        this.people = people;
    }
}
