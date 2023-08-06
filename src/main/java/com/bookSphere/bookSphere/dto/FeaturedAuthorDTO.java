package com.bookSphere.bookSphere.dto;

import com.bookSphere.bookSphere.models.FeaturedAuthor;

public class FeaturedAuthorDTO {

    public String firstName, lastName, nationality, genre, description, img;
    private Long id;
    private boolean isDeleted;

    public FeaturedAuthorDTO() {
    }

    public FeaturedAuthorDTO(FeaturedAuthor featuredAuthor) {
        firstName = featuredAuthor.getFirstName();
        lastName = featuredAuthor.getLastName();
        nationality = featuredAuthor.getNationality();
        genre = featuredAuthor.getGenre();
       description = featuredAuthor.getDescription();
        img = featuredAuthor.getImg();
        id= featuredAuthor.getId();
        isDeleted= featuredAuthor.isDeleted();
    }

    public Long getId() {
        return id;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNationality() {
        return nationality;
    }

    public String getGenre() {
        return genre;
    }

    public String getDescription() {
        return description;
    }

    public String getImg() {
        return img;
    }
}
