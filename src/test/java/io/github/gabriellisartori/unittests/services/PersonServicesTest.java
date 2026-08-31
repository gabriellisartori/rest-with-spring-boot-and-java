package io.github.gabriellisartori.unittests.services;

import io.github.gabriellisartori.class11_above.data.dto.PersonDTO;
import io.github.gabriellisartori.class11_above.model.Person;
import io.github.gabriellisartori.class11_above.repository.PersonRepository;
import io.github.gabriellisartori.exception.RequiredObjectIsNullException;
import io.github.gabriellisartori.class11_above.services.PersonServices;
import io.github.gabriellisartori.unittests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
class PersonServicesTest {

    MockPerson input;

    @InjectMocks
    private PersonServices services;

    @Mock
    PersonRepository repository;

    @Mock
    PagedResourcesAssembler<PersonDTO> assembler;

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
                .anyMatch(link -> link.getRel().value().equals("findAll") && link.getHref().startsWith("/api/person/v1") && link.getType().equals("GET")));

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
                .anyMatch(link -> link.getRel().value().equals("findAll") && link.getHref().startsWith("/api/person/v1") && link.getType().equals("GET")));

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
                .anyMatch(link -> link.getRel().value().equals("findAll") && link.getHref().startsWith("/api/person/v1") && link.getType().equals("GET")));

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

        Pageable pageable = PageRequest.of(0, 14, Sort.by(Sort.Direction.ASC, "firstName"));
        Page<Person> personPage = new PageImpl<>(personList, pageable, personList.size());

        when(repository.findAll(any(Pageable.class))).thenReturn(personPage);

        when(assembler.toModel(any(Page.class), any(Link.class))).thenAnswer(invocation -> {
            Page<PersonDTO> page = invocation.getArgument(0);
            List<EntityModel<PersonDTO>> models = page.getContent().stream()
                    .map(EntityModel::of)
                    .toList();
            return PagedModel.of(models, new PagedModel.PageMetadata(
                    page.getSize(), page.getNumber(), page.getTotalElements()));
        });

        PagedModel<EntityModel<PersonDTO>> result = services.findAll(pageable);

        assertNotNull(result);

        List<EntityModel<PersonDTO>> content = new ArrayList<>(result.getContent());
        assertEquals(14, content.size());

        var personOne = content.get(1).getContent();
        assertNotNull(personOne);
        assertNotNull(personOne.getId());
        assertNotNull(personOne.getFirstName());
        assertNotNull(personOne.getLastName());
        assertNotNull(personOne.getAddress());
        assertNotNull(personOne.getGender());
        assertNotNull(personOne.getLinks());

        assertTrue(personOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self") && link.getHref().endsWith("/api/person/v1/1") && link.getType().equals("GET")));

        assertTrue(personOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll") && link.getHref().startsWith("/api/person/v1") && link.getType().equals("GET")));

        assertTrue(personOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create") && link.getHref().endsWith("/api/person/v1") && link.getType().equals("POST")));

        assertTrue(personOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update") && link.getHref().endsWith("/api/person/v1") && link.getType().equals("PUT")));

        assertTrue(personOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete") && link.getHref().endsWith("/api/person/v1/1") && link.getType().equals("DELETE")));

        assertEquals("Address Test1", personOne.getAddress());
        assertEquals("First Name Test1", personOne.getFirstName());
        assertEquals("Female", personOne.getGender());
        assertEquals("Last Name Test1", personOne.getLastName());

        var personFour = content.get(4).getContent();
        assertNotNull(personFour);
        assertNotNull(personFour.getId());
        assertNotNull(personFour.getFirstName());
        assertNotNull(personFour.getLastName());
        assertNotNull(personFour.getAddress());
        assertNotNull(personFour.getGender());
        assertNotNull(personFour.getLinks());

        assertTrue(personFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self") && link.getHref().endsWith("/api/person/v1/4") && link.getType().equals("GET")));

        assertTrue(personFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll") && link.getHref().startsWith("/api/person/v1") && link.getType().equals("GET")));

        assertTrue(personFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create") && link.getHref().endsWith("/api/person/v1") && link.getType().equals("POST")));

        assertTrue(personFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update") && link.getHref().endsWith("/api/person/v1") && link.getType().equals("PUT")));

        assertTrue(personFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete") && link.getHref().endsWith("/api/person/v1/4") && link.getType().equals("DELETE")));

        assertEquals("Address Test4", personFour.getAddress());
        assertEquals("First Name Test4", personFour.getFirstName());
        assertEquals("Male", personFour.getGender());
        assertEquals("Last Name Test4", personFour.getLastName());

        var personSeven = content.get(7).getContent();
        assertNotNull(personSeven);
        assertNotNull(personSeven.getId());
        assertNotNull(personSeven.getFirstName());
        assertNotNull(personSeven.getLastName());
        assertNotNull(personSeven.getAddress());
        assertNotNull(personSeven.getGender());
        assertNotNull(personSeven.getLinks());

        assertTrue(personSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self") && link.getHref().endsWith("/api/person/v1/7") && link.getType().equals("GET")));

        assertTrue(personSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll") && link.getHref().startsWith("/api/person/v1") && link.getType().equals("GET")));

        assertTrue(personSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create") && link.getHref().endsWith("/api/person/v1") && link.getType().equals("POST")));

        assertTrue(personSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update") && link.getHref().endsWith("/api/person/v1") && link.getType().equals("PUT")));

        assertTrue(personSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete") && link.getHref().endsWith("/api/person/v1/7") && link.getType().equals("DELETE")));

        assertEquals("Address Test7", personSeven.getAddress());
        assertEquals("First Name Test7", personSeven.getFirstName());
        assertEquals("Female", personSeven.getGender());
        assertEquals("Last Name Test7", personSeven.getLastName());
    }
}