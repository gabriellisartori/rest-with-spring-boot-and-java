package io.github.gabriellisartori.class11_above.repository;

import io.github.gabriellisartori.class11_above.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {}
