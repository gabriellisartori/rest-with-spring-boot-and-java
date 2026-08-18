package io.github.gabriellisartori.class11_above.repository;

import io.github.gabriellisartori.class11_above.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
