package io.github.gabriellisartori.class11_above.file.exporter.factory;

import io.github.gabriellisartori.class11_above.file.exporter.MediaTypes;
import io.github.gabriellisartori.class11_above.file.exporter.contract.PersonExporter;
import io.github.gabriellisartori.class11_above.file.exporter.impl.CSVExporter;
import io.github.gabriellisartori.class11_above.file.exporter.impl.PDFExporter;
import io.github.gabriellisartori.class11_above.file.exporter.impl.XLSXExporter;
import io.github.gabriellisartori.exception.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class FileExporterFactory {

    private Logger logger = LoggerFactory.getLogger(FileExporterFactory.class);

    @Autowired
    private ApplicationContext context;

    public PersonExporter getExporter(String acceptHeader) throws Exception {
        if (acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_XLSX_VALUE)) {
            return context.getBean(XLSXExporter.class);
        } else if (acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_CSV_VALUE)) {
            return context.getBean(CSVExporter.class);
        } else if (acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_PDF_VALUE)) {
            return context.getBean(PDFExporter.class);
        } else {
            throw new BadRequestException("Unsupported file extension");
        }

    }
}
