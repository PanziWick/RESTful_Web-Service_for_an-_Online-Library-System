package com.example.Task4;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    Optional<Book>findByTitleAndAuthor(String title,Author author);

    List<Book>findByAuthor(Author author);
    List<Book>findBookByCategory(String category);

    @Transactional
    @Modifying
    @Query("DELETE FROM Book b WHERE b.author = :author")
    void deleteByAuthor(Author author);
}
