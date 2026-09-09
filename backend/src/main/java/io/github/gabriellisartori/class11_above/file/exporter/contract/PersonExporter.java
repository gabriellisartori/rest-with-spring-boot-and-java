package io.github.gabriellisartori.class11_above.file.exporter.contract;

import io.github.gabriellisartori.class11_above.data.dto.PersonDTO;
import org.springframework.core.io.Resource;

import java.util.List;

public interface PersonExporter {

    Resource exportPeople(List<PersonDTO> people) throws Exception;
    Resource exportPerson(PersonDTO person) throws Exception;
}
