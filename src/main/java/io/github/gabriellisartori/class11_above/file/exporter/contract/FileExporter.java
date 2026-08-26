package io.github.gabriellisartori.class11_above.file.exporter.contract;

import io.github.gabriellisartori.class11_above.data.dto.PersonDTO;
import org.springframework.core.io.Resource;

import java.util.List;

public interface FileExporter {

    Resource exportFile(List<PersonDTO> people) throws Exception;
}
