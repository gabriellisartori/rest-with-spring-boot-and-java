package io.github.gabriellisartori.class11_above.services.services;

import io.github.gabriellisartori.class11_above.controllers.BookController;
import io.github.gabriellisartori.class11_above.data.dto.BookDTO;
import io.github.gabriellisartori.class11_above.model.Book;
import io.github.gabriellisartori.class11_above.repository.BookRepository;
import io.github.gabriellisartori.class6_10.controllers.TestLogController;
import io.github.gabriellisartori.exception.RequiredObjectIsNullException;
import io.github.gabriellisartori.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.github.gabriellisartori.class6_10.mapper.ObjectMapperV1.parseListObjects;
import static io.github.gabriellisartori.class6_10.mapper.ObjectMapperV1.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookServices {
    private Logger logger = LoggerFactory.getLogger(TestLogController.class);

    @Autowired
    BookRepository repository;

    public List<BookDTO> findAll() {
        logger.info("Finding all books");

        var books = parseListObjects(repository.findAll(), BookDTO.class);

        books.forEach(this::addHateoasLink);

        return books;
    }

    public BookDTO findById(Long id) {
        logger.info("Finding one book");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        var dto = parseObject(entity, BookDTO.class);

        addHateoasLink(dto);

        return dto;
    }

    public BookDTO create(BookDTO book) {
        if (book == null) {
            throw new RequiredObjectIsNullException("It is not allowed to persist a null object!");
        }

        logger.info("Creating one book");

        var entity = parseObject(book, Book.class);

        var dto = parseObject(repository.save(entity), BookDTO.class);

        addHateoasLink(dto);

        return dto;
    }

    public BookDTO update(BookDTO book) {
        if (book == null) {
            throw new RequiredObjectIsNullException("It is not allowed to persist a null object!");
        }

        logger.info("Updating one book");

        Book entity = repository.findById(book.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        entity.setTitle(book.getTitle());
        entity.setAuthor(book.getAuthor());
        entity.setPrice(book.getPrice());
        entity.setLaunchDate(book.getLaunchDate());

        var dto = parseObject(repository.save(entity), BookDTO.class);

        addHateoasLink(dto);

        return dto;
    }

    public void delete(Long id) {
        logger.info("Deleting one book");

        Book entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        repository.delete(entity);
    }

    private void addHateoasLink(BookDTO dto) {
        dto.add(linkTo(methodOn(BookController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(BookController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
        dto.add(linkTo(methodOn(BookController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(BookController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(BookController.class).update(dto)).withRel("update").withType("PUT"));
    }
}
