package io.github.gabriellisartori.integrationtests.controllers.withxml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.github.gabriellisartori.config.TestConfigs;
import io.github.gabriellisartori.integrationtests.dto.BookDTO;
import io.github.gabriellisartori.integrationtests.dto.wrappers.xml_yaml.PagedModelBook;
import io.github.gabriellisartori.integrationtests.testcontainers.AbstractIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.Date;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookControllerXmlTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static XmlMapper xmlMapper;
    private static BookDTO book;

    @BeforeAll
    static void setUp() {
        xmlMapper = new XmlMapper();
        xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        book = new BookDTO();
    }

    @Test
    @Order(1)
    void createTest() throws JsonProcessingException {
        mockBook();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
                .setBasePath("/api/book/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .accept(MediaType.APPLICATION_XML_VALUE)
                .body(book)
                .when()
                .post()
                .then()
                .statusCode(201)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .extract()
                .body()
                .asString();

        BookDTO createdBook = xmlMapper.readValue(content, BookDTO.class);
        book = createdBook;

        assertNotNull(createdBook.getId());
        assertNotNull(createdBook.getLaunchDate());
        assertTrue(createdBook.getId() > 0);
        assertEquals("Author Test", createdBook.getAuthor());
        assertEquals("Title Test", createdBook.getTitle());
        assertEquals(25D, createdBook.getPrice());
    }

    @Test
    @Order(2)
    void updateTest() throws JsonProcessingException {
        book.setTitle("Title");

        var content = given(specification)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .accept(MediaType.APPLICATION_XML_VALUE)
                .body(book)
                .when()
                .put()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .extract()
                .body()
                .asString();

        BookDTO createdBook = xmlMapper.readValue(content, BookDTO.class);
        book = createdBook;

        assertNotNull(createdBook.getId());
        assertNotNull(createdBook.getLaunchDate());
        assertTrue(createdBook.getId() > 0);
        assertEquals("Author Test", createdBook.getAuthor());
        assertEquals("Title", createdBook.getTitle());
        assertEquals(25D, createdBook.getPrice());
    }

    @Test
    @Order(3)
    void findByIdTest() throws JsonProcessingException {
        var content = given(specification)
                .accept(MediaType.APPLICATION_XML_VALUE)
                .pathParam("id", book.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .extract()
                .body()
                .asString();

        BookDTO createdBook = xmlMapper.readValue(content, BookDTO.class);

        assertNotNull(createdBook.getId());
        assertNotNull(createdBook.getLaunchDate());
        assertTrue(createdBook.getId() > 0);
        assertEquals("Author Test", createdBook.getAuthor());
        assertEquals("Title", createdBook.getTitle());
        assertEquals(25D, createdBook.getPrice());
    }

    @Test
    @Order(4)
    void deleteTest() throws JsonProcessingException {
        given(specification)
                .pathParam("id", book.getId())
                .when()
                .delete("{id}")
                .then()
                .statusCode(204);
    }

    @Test
    @Order(5)
    void findAllTest() throws JsonProcessingException {
        var content = given(specification)
                .accept(MediaType.APPLICATION_XML_VALUE)
                .queryParam("page", 2, "size", 5, "direction", "asc")
                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .extract()
                .body()
                .asString();

        PagedModelBook wrapper = xmlMapper.readValue(content, PagedModelBook.class);
        List<BookDTO> books = wrapper.getContent();

        BookDTO bookOne = books.get(0);

        assertNotNull(bookOne.getId());
        assertTrue(bookOne.getId() > 0);
        assertNotNull(bookOne.getLaunchDate());

        assertEquals("Mike Cohn", bookOne.getAuthor());
        assertEquals("Agile Estimating and Planning", bookOne.getTitle());
        assertEquals(143.83, bookOne.getPrice());

        BookDTO bookThree = books.get(2);

        assertNotNull(bookThree.getId());
        assertTrue(bookThree.getId() > 0);
        assertNotNull(bookThree.getLaunchDate());

        assertEquals("Mike Cohn", bookThree.getAuthor());
        assertEquals("Agile Estimating and Planning", bookThree.getTitle());
        assertEquals(35.67, bookThree.getPrice());
    }

    private void mockBook() {
        book.setAuthor("Author Test");
        book.setTitle("Title Test");
        book.setPrice(25D);
        book.setLaunchDate(new Date());
    }
}