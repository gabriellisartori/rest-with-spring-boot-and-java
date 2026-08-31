package io.github.gabriellisartori.unittests.services;

import io.github.gabriellisartori.class11_above.data.dto.BookDTO;
import io.github.gabriellisartori.class11_above.model.Book;
import io.github.gabriellisartori.class11_above.repository.BookRepository;
import io.github.gabriellisartori.exception.RequiredObjectIsNullException;
import io.github.gabriellisartori.class11_above.services.BookServices;
import io.github.gabriellisartori.unittests.mapper.mocks.MockBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServicesTest {

    MockBook input;

    @InjectMocks
    private BookServices services;

    @Mock
    BookRepository repository;

    @Mock
    PagedResourcesAssembler<BookDTO> assembler;

    @BeforeEach
    void setUp() {
        input = new MockBook();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {
        Book book = input.mockEntity(1);
        book.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(book));

        var result = services.findById(1L);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getAuthor());
        assertNotNull(result.getTitle());
        assertNotNull(result.getPrice());
        assertNotNull(result.getLaunchDate());
        assertNotNull(result.getLinks());

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self") && link.getHref().endsWith("/api/book/v1/1") && link.getType().equals("GET")));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll") && link.getHref().endsWith("/api/book/v1") && link.getType().equals("GET")));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create") && link.getHref().endsWith("/api/book/v1") && link.getType().equals("POST")));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update") && link.getHref().endsWith("/api/book/v1/1") && link.getType().equals("PUT")));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete") && link.getHref().endsWith("/api/book/v1/1") && link.getType().equals("DELETE")));

        assertEquals("Author Test1", result.getAuthor());
        assertEquals("Title Test1", result.getTitle());
        assertEquals(25D, result.getPrice(), 0.01);
        assertNotNull(result.getLaunchDate());
    }

    @Test
    void create() {
        Book book = input.mockEntity(1);
        Book persisted = book;

        persisted.setId(1L);

        BookDTO dto = input.mockDTO(1);

        when(repository.save(book)).thenReturn(persisted);

        var result = services.create(dto);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getAuthor());
        assertNotNull(result.getTitle());
        assertNotNull(result.getPrice());
        assertNotNull(result.getLaunchDate());
        assertNotNull(result.getLinks());

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self") && link.getHref().endsWith("/api/book/v1/1") && link.getType().equals("GET")));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll") && link.getHref().endsWith("/api/book/v1") && link.getType().equals("GET")));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create") && link.getHref().endsWith("/api/book/v1") && link.getType().equals("POST")));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update") && link.getHref().endsWith("/api/book/v1") && link.getType().equals("PUT")));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete") && link.getHref().endsWith("/api/book/v1/1") && link.getType().equals("DELETE")));

        assertEquals("Author Test1", result.getAuthor());
        assertEquals("Title Test1", result.getTitle());
        assertEquals(25D, result.getPrice(), 0.01);
        assertNotNull(result.getLaunchDate());
    }

    @Test
    void testCreateWithNullBook() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            services.create(null);
        });

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void update() {
        Book book = input.mockEntity(1);
        Book persisted = book;

        persisted.setId(1L);

        BookDTO dto = input.mockDTO(1);

        when(repository.findById(1L)).thenReturn(Optional.of(book));
        when(repository.save(book)).thenReturn(persisted);

        var result = services.update(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getAuthor());
        assertNotNull(result.getTitle());
        assertNotNull(result.getPrice());
        assertNotNull(result.getLaunchDate());
        assertNotNull(result.getLinks());

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self") && link.getHref().endsWith("/api/book/v1/1") && link.getType().equals("GET")));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll") && link.getHref().endsWith("/api/book/v1") && link.getType().equals("GET")));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create") && link.getHref().endsWith("/api/book/v1") && link.getType().equals("POST")));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update") && link.getHref().endsWith("/api/book/v1") && link.getType().equals("PUT")));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete") && link.getHref().endsWith("/api/book/v1/1") && link.getType().equals("DELETE")));

        assertEquals("Author Test1", result.getAuthor());
        assertEquals("Title Test1", result.getTitle());
        assertEquals(25D, result.getPrice(), 0.01);
        assertNotNull(result.getLaunchDate());
    }

    @Test
    void testUpdateWithNullBook() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            services.update(null);
        });

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void delete() {
        Book book = input.mockEntity(1);
        book.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(book));

        services.delete(1L);

        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).delete(any(Book.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    void findAll() {
        List<Book> bookList = input.mockEntityList();

        Pageable pageable = PageRequest.of(0, 14, Sort.by(Sort.Direction.ASC, "title"));
        Page<Book> bookPage = new PageImpl<>(bookList, pageable, bookList.size());

        when(repository.findAll(any(Pageable.class))).thenReturn(bookPage);

        when(assembler.toModel(any(Page.class), any(Link.class))).thenAnswer(invocation -> {
            Page<BookDTO> page = invocation.getArgument(0);
            List<EntityModel<BookDTO>> models = page.getContent().stream()
                    .map(EntityModel::of)
                    .toList();
            return PagedModel.of(models, new PagedModel.PageMetadata(
                    page.getSize(), page.getNumber(), page.getTotalElements()));
        });

        PagedModel<EntityModel<BookDTO>> result = services.findAll(pageable);

        assertNotNull(result);

        List<EntityModel<BookDTO>> content = new ArrayList<>(result.getContent());
        assertEquals(14, content.size());

        var bookOne = content.get(1).getContent();
        assertNotNull(bookOne);
        assertNotNull(bookOne.getId());
        assertNotNull(bookOne.getAuthor());
        assertNotNull(bookOne.getTitle());
        assertNotNull(bookOne.getPrice());
        assertNotNull(bookOne.getLaunchDate());
        assertNotNull(bookOne.getLinks());

        assertTrue(bookOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self") && link.getHref().endsWith("/api/book/v1/1") && link.getType().equals("GET")));

        assertTrue(bookOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll") && link.getHref().startsWith("/api/book/v1") && link.getType().equals("GET")));

        assertTrue(bookOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create") && link.getHref().endsWith("/api/book/v1") && link.getType().equals("POST")));

        assertTrue(bookOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update") && link.getHref().endsWith("/api/book/v1") && link.getType().equals("PUT")));

        assertTrue(bookOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete") && link.getHref().endsWith("/api/book/v1/1") && link.getType().equals("DELETE")));

        assertEquals("Author Test1", bookOne.getAuthor());
        assertEquals("Title Test1", bookOne.getTitle());
        assertEquals(25D, bookOne.getPrice(), 0.01);
        assertNotNull(bookOne.getLaunchDate());


        var bookFour = content.get(4).getContent();
        assertNotNull(bookFour);
        assertNotNull(bookFour.getId());
        assertNotNull(bookFour.getAuthor());
        assertNotNull(bookFour.getTitle());
        assertNotNull(bookFour.getPrice());
        assertNotNull(bookFour.getLaunchDate());
        assertNotNull(bookFour.getLinks());

        assertTrue(bookFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self") && link.getHref().endsWith("/api/book/v1/4") && link.getType().equals("GET")));

        assertTrue(bookFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll") && link.getHref().startsWith("/api/book/v1") && link.getType().equals("GET")));

        assertTrue(bookFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create") && link.getHref().endsWith("/api/book/v1") && link.getType().equals("POST")));

        assertTrue(bookFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update") && link.getHref().endsWith("/api/book/v1") && link.getType().equals("PUT")));

        assertTrue(bookFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete") && link.getHref().endsWith("/api/book/v1/4") && link.getType().equals("DELETE")));

        assertEquals("Author Test4", bookFour.getAuthor());
        assertEquals("Title Test4", bookFour.getTitle());
        assertEquals(25D, bookFour.getPrice(), 0.01);
        assertNotNull(bookFour.getLaunchDate());


        var bookSeven = content.get(7).getContent();
        assertNotNull(bookSeven);
        assertNotNull(bookSeven.getId());
        assertNotNull(bookSeven.getAuthor());
        assertNotNull(bookSeven.getTitle());
        assertNotNull(bookSeven.getPrice());
        assertNotNull(bookSeven.getLaunchDate());
        assertNotNull(bookSeven.getLinks());

        assertTrue(bookSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self") && link.getHref().endsWith("/api/book/v1/7") && link.getType().equals("GET")));

        assertTrue(bookSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll") && link.getHref().startsWith("/api/book/v1") && link.getType().equals("GET")));

        assertTrue(bookSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create") && link.getHref().endsWith("/api/book/v1") && link.getType().equals("POST")));

        assertTrue(bookSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update") && link.getHref().endsWith("/api/book/v1") && link.getType().equals("PUT")));

        assertTrue(bookSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete") && link.getHref().endsWith("/api/book/v1/7") && link.getType().equals("DELETE")));

        assertEquals("Author Test7", bookSeven.getAuthor());
        assertEquals("Title Test7", bookSeven.getTitle());
        assertEquals(25D, bookSeven.getPrice(), 0.01);
        assertNotNull(bookSeven.getLaunchDate());
    }
}