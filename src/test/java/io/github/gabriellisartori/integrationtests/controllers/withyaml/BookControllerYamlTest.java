package io.github.gabriellisartori.integrationtests.controllers.withyaml;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.gabriellisartori.config.TestConfigs;
import io.github.gabriellisartori.integrationtests.controllers.withyaml.mapper.YAMLMapper;
import io.github.gabriellisartori.integrationtests.dto.AccountCredentialsDTO;
import io.github.gabriellisartori.integrationtests.dto.BookDTO;
import io.github.gabriellisartori.integrationtests.dto.TokenDTO;
import io.github.gabriellisartori.integrationtests.dto.wrappers.xml_yaml.PagedModelBook;
import io.github.gabriellisartori.integrationtests.testcontainers.AbstractIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.Date;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookControllerYamlTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static YAMLMapper yamlMapper;
    private static BookDTO book;
    private static TokenDTO token;

    @BeforeAll
    static void setUp() {
        yamlMapper = new YAMLMapper();
        book = new BookDTO();
        token = new TokenDTO();
    }

    @Order(0)
    @Test
    void signin() throws JsonProcessingException {
        AccountCredentialsDTO credentials = new AccountCredentialsDTO("leandro", "admin123");

        token = given()
                .config(
                        RestAssuredConfig
                                .config()
                                .encoderConfig(
                                        EncoderConfig
                                                .encoderConfig()
                                                .encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT))
                )
                .basePath("/auth/signin")
                .port(TestConfigs.SERVER_PORT)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .body(credentials, yamlMapper)
                .when()
                .post()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                .body()
                .as(TokenDTO.class, yamlMapper);

        assertNotNull(token.getAccessToken());
        assertNotNull(token.getRefreshToken());

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
                .addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + token.getAccessToken())
                .setBasePath("/api/book/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }

    @Test
    @Order(1)
    void createTest() throws JsonProcessingException {
        mockBook();

        var createdBook =
                given()
                        .config(
                                RestAssuredConfig
                                        .config()
                                        .encoderConfig(
                                                EncoderConfig
                                                        .encoderConfig()
                                                        .encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT))
                        )
                        .spec(specification)
                        .contentType(MediaType.APPLICATION_YAML_VALUE)
                        .accept(MediaType.APPLICATION_YAML_VALUE)
                        .body(book, yamlMapper)
                        .when()
                        .post()
                        .then()
                        .statusCode(201)
                        .contentType(MediaType.APPLICATION_YAML_VALUE)
                        .extract()
                        .body()
                        .as(BookDTO.class, yamlMapper);

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

        var updateBook = given()
                .config(
                        RestAssuredConfig
                                .config()
                                .encoderConfig(
                                        EncoderConfig
                                                .encoderConfig()
                                                .encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT))
                )
                .spec(specification)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .body(book, yamlMapper)
                .when()
                .put()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                .body()
                .as(BookDTO.class, yamlMapper);

        book = updateBook;

        assertNotNull(updateBook.getId());
        assertNotNull(updateBook.getLaunchDate());
        assertTrue(updateBook.getId() > 0);
        assertEquals("Author Test", updateBook.getAuthor());
        assertEquals("Title", updateBook.getTitle());
        assertEquals(25D, updateBook.getPrice());
    }

    @Test
    @Order(3)
    void findByIdTest() throws JsonProcessingException {
        var createdBook = given()
                .config(
                        RestAssuredConfig
                                .config()
                                .encoderConfig(
                                        EncoderConfig
                                                .encoderConfig()
                                                .encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT))
                )
                .spec(specification)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .pathParam("id", book.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                .body()
                .as(BookDTO.class, yamlMapper);

        book = createdBook;

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
        var response = given(specification)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .queryParam("page", 2, "size", 5, "direction", "asc")
                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                .body()
                .as(PagedModelBook.class, yamlMapper);

        List<BookDTO> books = response.getContent();

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