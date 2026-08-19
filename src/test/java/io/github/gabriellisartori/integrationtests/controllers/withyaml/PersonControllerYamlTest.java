package io.github.gabriellisartori.integrationtests.controllers.withyaml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import io.github.gabriellisartori.config.TestConfigs;
import io.github.gabriellisartori.integrationtests.controllers.withyaml.mapper.YAMLMapper;
import io.github.gabriellisartori.integrationtests.dto.PersonDTO;
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

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllerYamlTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static YAMLMapper yamlMapper;
    private static PersonDTO person;

    @BeforeAll
    static void setUp() {
        yamlMapper = new YAMLMapper();
        person = new PersonDTO();
    }

    @Test
    @Order(1)
    void createTest() throws JsonProcessingException {
        mockPerson();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var createdPerson =
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
                        .body(person, yamlMapper)
                        .when()
                        .post()
                        .then()
                        .statusCode(201)
                        .contentType(MediaType.APPLICATION_YAML_VALUE)
                        .extract()
                        .body()
                        .as(PersonDTO.class, yamlMapper);

        person = createdPerson;

        assertNotNull(createdPerson.getId());
        assertTrue(createdPerson.getId() > 0);
        assertEquals("Eduardo", createdPerson.getFirstName());
        assertEquals("Algo", createdPerson.getLastName());
        assertEquals("Rua 1", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());
        assertTrue(createdPerson.getEnabled());
    }

    @Test
    @Order(2)
    void updateTest() throws JsonProcessingException {
        person.setLastName("Caron");

        var updatePerson = given()
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
                .body(person, yamlMapper)
                .when()
                .put()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                .body()
                .as(PersonDTO.class, yamlMapper);

        person = updatePerson;

        assertNotNull(updatePerson.getId());
        assertTrue(updatePerson.getId() > 0);
        assertEquals("Eduardo", updatePerson.getFirstName());
        assertEquals("Caron", updatePerson.getLastName());
        assertEquals("Rua 1", updatePerson.getAddress());
        assertEquals("Male", updatePerson.getGender());
        assertTrue(updatePerson.getEnabled());
    }

    @Test
    @Order(3)
    void findByIdTest() throws JsonProcessingException {
        var createdPerson = given()
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
                .pathParam("id", person.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                .body()
                .as(PersonDTO.class, yamlMapper);

        person = createdPerson;

        assertNotNull(createdPerson.getId());
        assertTrue(createdPerson.getId() > 0);
        assertEquals("Eduardo", createdPerson.getFirstName());
        assertEquals("Caron", createdPerson.getLastName());
        assertEquals("Rua 1", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());
        assertTrue(createdPerson.getEnabled());
    }

    @Test
    @Order(4)
    void disableTest() throws JsonProcessingException {
        var createdPerson = given()
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
                .pathParam("id", person.getId())
                .when()
                .patch("{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                .body()
                .as(PersonDTO.class, yamlMapper);

        person = createdPerson;

        assertNotNull(createdPerson.getId());
        assertTrue(createdPerson.getId() > 0);
        assertEquals("Eduardo", createdPerson.getFirstName());
        assertEquals("Caron", createdPerson.getLastName());
        assertEquals("Rua 1", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());
        assertFalse(createdPerson.getEnabled());
    }

    @Test
    @Order(5)
    void deleteTest() throws JsonProcessingException {
        given(specification)
                .pathParam("id", person.getId())
                .when()
                .delete("{id}")
                .then()
                .statusCode(204);
    }

    @Test
    @Order(6)
    void findAllTest() throws JsonProcessingException {
        var content = given(specification)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                .body()
                .as(PersonDTO[].class, yamlMapper);

        List<PersonDTO> people = Arrays.asList(content);

        PersonDTO personOne = people.get(0);

        assertNotNull(personOne.getId());
        assertTrue(personOne.getId() > 0);
        assertEquals("Gabrielli", personOne.getFirstName());
        assertEquals("Sartori", personOne.getLastName());
        assertEquals("Lajeado/RS", personOne.getAddress());
        assertEquals("female", personOne.getGender());
        assertTrue(personOne.getEnabled());

        PersonDTO personThree = people.get(2);

        assertNotNull(personThree.getId());
        assertTrue(personThree.getId() > 0);
        assertEquals("Eduardo", personThree.getFirstName());
        assertEquals("Caron", personThree.getLastName());
        assertEquals("Não informadooooo", personThree.getAddress());
        assertEquals("male", personThree.getGender());
        assertTrue(personThree.getEnabled());
    }

    private void mockPerson() {
        person.setFirstName("Eduardo");
        person.setLastName("Algo");
        person.setAddress("Rua 1");
        person.setGender("Male");
        person.setEnabled(true);
    }
}