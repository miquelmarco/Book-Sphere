package com.bookSphere.bookSphere.models;
import javax.persistence.*;

@Entity
public class FeaturedAuthor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String firstName, lastName, nationality, genre, img;
    @Column(length = 1000)
    public String description;
    public boolean isDeleted;
    public FeaturedAuthor() {
    }
    public FeaturedAuthor(String firstName, String lastName, String nationality, String genre, String description, String img,boolean isDeleted) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationality = nationality;
        this.genre = genre;
        this.description = description;
        this.img = img;
        this.isDeleted=isDeleted;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Long getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getNationality() {
        return nationality;
    }
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getImg() {
        return img;
    }
    public void setImg(String img) {
        this.img = img;
    }
}