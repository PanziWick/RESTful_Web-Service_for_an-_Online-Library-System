package com.example.Task4;

import jakarta.persistence.*;

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aid;
    private String name;
    private String nationality;

    public Author(Long aid, String name, String nationality) {
        this.aid = aid;
        this.name = name;
        this.nationality = nationality;
    }

    public Author() {
    }
    public Long getAid() {
        return aid;
    }
    public void setAid(Long aid) {
        this.aid = aid;
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
    public Author(String name, String nationality) {
    }
    public void setId(Long id) {
    }
}
