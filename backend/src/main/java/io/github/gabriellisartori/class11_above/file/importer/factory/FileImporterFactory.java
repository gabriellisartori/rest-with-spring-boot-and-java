package io.github.gabriellisartori.class11_above.file.importer.factory;

import io.github.gabriellisartori.class11_above.file.importer.contract.FileImporter;
import io.github.gabriellisartori.class11_above.file.importer.impl.CSVImporter;
import io.github.gabriellisartori.class11_above.file.importer.impl.XLSXImporter;
import io.github.gabriellisartori.exception.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class FileImporterFactory {

    private Logger logger = LoggerFactory.getLogger(FileImporterFactory.class);

    @Autowired
    private ApplicationContext context;

    public FileImporter getImporter(String fileName) throws Exception {
        if (fileName.endsWith(".xlsx")) {
            //return new XLSXImporter();
            return context.getBean(XLSXImporter.class);
        } else if (fileName.endsWith(".csv")) {
            //return new CSVImporter();
            return context.getBean(CSVImporter.class);
        } else {
            throw new BadRequestException("Unsupported file extension");
        }

    }
}
