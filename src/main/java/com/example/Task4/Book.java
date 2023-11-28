package com.example.Task4;

import jakarta.persistence.*;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String category;
    private double price;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "Author_id")
    private Author author;

    public Book(Long id, String title, String category, double price, Author author) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.price = price;
        this.author = author;
    }

    public Book(Long id, String title, String category, double price){
    }

    public Book() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
