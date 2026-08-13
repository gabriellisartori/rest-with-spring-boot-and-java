package io.github.gabriellisartori.class6_7.repository;

import io.github.gabriellisartori.class6_7.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {}
