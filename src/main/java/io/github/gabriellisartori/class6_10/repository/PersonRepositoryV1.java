package io.github.gabriellisartori.class6_10.repository;

import io.github.gabriellisartori.class6_10.model.PersonV1;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepositoryV1 extends JpaRepository<PersonV1, Long> {}
