package com.example.Task4;

import java.util.List;
import java.util.stream.Collectors;

public class AuthorWithBook_Response {
    private Long authorId;
    private String authorName;
    private List<BookResponse> books;

    public AuthorWithBook_Response(Author author, List<Book> books) {
        this.authorId = author.getAid();
        this.authorName = author.getName();
        this.books = books.stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public List<BookResponse> getBooks() {
        return books;
    }

    public void setBooks(List<BookResponse> books) {
        this.books = books;
    }
}
