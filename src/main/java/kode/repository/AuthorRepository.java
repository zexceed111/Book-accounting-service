package kode.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import kode.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Page<Author> findByFirstNameContainingIgnoreCase(String firstName, Pageable pageable);
    Page<Author> findByLastNameContainingIgnoreCase(String lastName, Pageable pageable);
}