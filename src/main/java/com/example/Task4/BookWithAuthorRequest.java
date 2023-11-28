//DTO data transfer object

package com.example.Task4;

public class BookWithAuthorRequest {
    private String title;
    private String category;
    private double price;
    private String name;
    private String nationality;

    public BookWithAuthorRequest(String title, String category, double price, String name, String nationality) {
        this.title = title;
        this.category = category;
        this.price = price;
        this.name = name;
        this.nationality = nationality;
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

    public double getAid() {
        return price;
    }

    public void setAid(Long aid) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}