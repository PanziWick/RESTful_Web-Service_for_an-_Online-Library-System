package com.example.Task4;

public class BookResponse {
    private Long bookId;
    private String title;
    private String category;
    private double price;

    public BookResponse(Book book){
        this.bookId = book.getId();
        this.title = book.getTitle();
        this.category = book.getCategory();
        this.price = book.getPrice();
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
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
}
