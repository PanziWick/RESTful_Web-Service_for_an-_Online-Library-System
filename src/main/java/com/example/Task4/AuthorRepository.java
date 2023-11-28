package com.example.Task4;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long>{

    List<Author> findByNationality(String nationality);

    Optional<Author> findByName(String authorName);
}
