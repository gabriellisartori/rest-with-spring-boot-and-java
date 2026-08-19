package io.github.gabriellisartori.unittests.services;

import io.github.gabriellisartori.class11_above.data.dto.PersonDTO;
import io.github.gabriellisartori.class11_above.model.Person;
import io.github.gabriellisartori.class11_above.repository.PersonRepository;
import io.github.gabriellisartori.exception.RequiredObjectIsNullException;
import io.github.gabriellisartori.services.PersonServices;
import io.github.gabriellisartori.unittests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServicesTest {

    MockPerson input;

    @InjectMocks
    private PersonServices services;

    @Mock
    PersonRepository repository;

    @BeforeEach
    void setUp() {
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {
        Person person = input.mockEntity(1);
        person.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(person));

        var result = services.findById(1L);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getFirstName());
        assertNotNull(result.getLastName());
        assertNotNull(result.getAddress());
        assertNotNull(result.getGender());
        assertNotNull(result.getLinks());

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self") && link.getHref().endsWith("/api/person/v1/1") && link.getType().equals("GET")));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll") && link.getHref().endsWith("/api/person/v1") && link.getType().equals("GET")));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create") && link.getHref().endsWith("/api/person/v1") && link.getType().equals("POST")));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update") && link.getHref().endsWith("/api/person/v1") && link.getType().equals("PUT")));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete") && link.getHref().endsWith("/api/person/v1/1") && link.getType().equals("DELETE")));

        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Female", result.getGender());
        assertEquals("Last Name Test1", result.getLastName());
    }

    @Test
    void create() {
        Person person = input.mockEntity(1);
        Person persisted = person;

        persisted.setId(1L);

        PersonDTO dto = input.mockDTO(1);

        when(repository.save(person)).thenReturn(persisted);

        var result = services.create(dto);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getFirstName());
        assertNotNull(result.getLastName());
        assertNotNull(result.getAddress());
        assertNotNull(result.getGender());
        assertNotNull(result.getLinks());

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self") && link.getHref().endsWith("/api/person/v1/1") && link.getType().equals("GET")));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll") && link.getHref().endsWith("/api/person/v1") && link.getType().equals("GET")));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create") && link.getHref().endsWith("/api/person/v1") && link.getType().equals("POST")));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update") && link.getHref().endsWith("/api/person/v1") && link.getType().equals("PUT")));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete") && link.getHref().endsWith("/api/person/v1/1") && link.getType().equals("DELETE")));

        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Female", result.getGender());
        assertEquals("Last Name Test1", result.getLastName());
    }

    @Test
    void testCreateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            services.create(null);
        });

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void update() {
        Person person = input.mockEntity(1);
        Person persisted = person;

        persisted.setId(1L);

        PersonDTO dto = input.mockDTO(1);

        when(repository.findById(1L)).thenReturn(Optional.of(person));
        when(repository.save(person)).thenReturn(persisted);

        var result = services.update(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getFirstName());
        assertNotNull(result.getLastName());
        assertNotNull(result.getAddress());
        assertNotNull(result.getGender());
        assertNotNull(result.getLinks());

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self") && link.getHref().endsWith("/api/person/v1/1") && link.getType().equals("GET")));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll") && link.getHref().endsWith("/api/person/v1") && link.getType().equals("GET")));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create") && link.getHref().endsWith("/api/person/v1") && link.getType().equals("POST")));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update") && link.getHref().endsWith("/api/person/v1") && link.getType().equals("PUT")));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete") && link.getHref().endsWith("/api/person/v1/1") && link.getType().equals("DELETE")));

        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Female", result.getGender());
        assertEquals("Last Name Test1", result.getLastName());
    }

    @Test
    void testUpdateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            services.update(null);
        });

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void delete() {
        Person person = input.mockEntity(1);
        person.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(person));

        services.delete(1L);

        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).delete(any(Person.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    void findAll() {
        List<Person> personList = input.mockEntityList();

        when(repository.findAll()).thenReturn(personList);

        List<PersonDTO> result = services.findAll();

        assertNotNull(result);
        assertEquals(14, result.size());

        var personOne = result.get(1);
        assertNotNull(personOne);
        assertNotNull(personOne.getId());
        assertNotNull(personOne.getFirstName());
        assertNotNull(personOne.getLastName());
        assertNotNull(personOne.getAddress());
        assertNotNull(personOne.getGender());
        assertNotNull(personOne.getLinks());

        assertNotNull(personOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self") && link.getHref().endsWith("/api/person/v1/1") && link.getType().equals("GET")));

        assertNotNull(personOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll") && link.getHref().endsWith("/api/person/v1") && link.getType().equals("GET")));

        assertNotNull(personOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create") && link.getHref().endsWith("/api/person/v1") && link.getType().equals("POST")));

        assertNotNull(personOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update") && link.getHref().endsWith("/api/person/v1") && link.getType().equals("PUT")));

        assertNotNull(personOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete") && link.getHref().endsWith("/api/person/v1/1") && link.getType().equals("DELETE")));

        assertEquals("Address Test1", personOne.getAddress());
        assertEquals("First Name Test1", personOne.getFirstName());
        assertEquals("Female", personOne.getGender());
        assertEquals("Last Name Test1", personOne.getLastName());


        var personFour = result.get(4);
        assertNotNull(personFour);
        assertNotNull(personFour.getId());
        assertNotNull(personFour.getFirstName());
        assertNotNull(personFour.getLastName());
        assertNotNull(personFour.getAddress());
        assertNotNull(personFour.getGender());
        assertNotNull(personFour.getLinks());

        assertNotNull(personFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self") && link.getHref().endsWith("/api/person/v1/4") && link.getType().equals("GET")));

        assertNotNull(personFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll") && link.getHref().endsWith("/api/person/v1") && link.getType().equals("GET")));

        assertNotNull(personFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create") && link.getHref().endsWith("/api/person/v1") && link.getType().equals("POST")));

        assertNotNull(personFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update") && link.getHref().endsWith("/api/person/v1") && link.getType().equals("PUT")));

        assertNotNull(personFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete") && link.getHref().endsWith("/api/person/v1/4") && link.getType().equals("DELETE")));

        assertEquals("Address Test4", personFour.getAddress());
        assertEquals("First Name Test4", personFour.getFirstName());
        assertEquals("Male", personFour.getGender());
        assertEquals("Last Name Test4", personFour.getLastName());


        var personSeven = result.get(7);
        assertNotNull(personSeven);
        assertNotNull(personSeven.getId());
        assertNotNull(personSeven.getFirstName());
        assertNotNull(personSeven.getLastName());
        assertNotNull(personSeven.getAddress());
        assertNotNull(personSeven.getGender());
        assertNotNull(personSeven.getLinks());

        assertNotNull(personSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self") && link.getHref().endsWith("/api/person/v1/7") && link.getType().equals("GET")));

        assertNotNull(personSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll") && link.getHref().endsWith("/api/person/v1") && link.getType().equals("GET")));

        assertNotNull(personSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create") && link.getHref().endsWith("/api/person/v1") && link.getType().equals("POST")));

        assertNotNull(personSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update") && link.getHref().endsWith("/api/person/v1") && link.getType().equals("PUT")));

        assertNotNull(personSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete") && link.getHref().endsWith("/api/person/v1/7") && link.getType().equals("DELETE")));

        assertEquals("Address Test7", personSeven.getAddress());
        assertEquals("First Name Test7", personSeven.getFirstName());
        assertEquals("Female", personSeven.getGender());
        assertEquals("Last Name Test7", personSeven.getLastName());
    }
}