package io.github.gabriellisartori.class11_above.file.importer.contract;

import io.github.gabriellisartori.class11_above.data.dto.PersonDTO;

import java.io.InputStream;
import java.util.List;

public interface FileImporter {

    List<PersonDTO> importFile(InputStream inputStream) throws Exception;
}
