package com.bookSphere.bookSphere.dto;

import com.bookSphere.bookSphere.models.Book;


public class BookDTO {
    private Long id;
    private String isbn, title, author, category, description, img;
    private Integer stock;
    private Double price;
    private Integer discount;
    private boolean isDeleted;

    public BookDTO() {
    }

    public BookDTO(Book book) {
        id= book.getId();
        isbn = book.getIsbn();
        title = book.getTitle();
        author = book.getAutor();
        category= book.getCategory();
        description = book.getDescription();
        img = book.getImg();
       stock= book.getStock();
        price = book.getPrice();
       discount = book.getDiscount();
       isDeleted=book.isDeleted();
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public Long getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }


    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getImg() {
        return img;
    }

    public Integer getStock() {
        return stock;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getDiscount() {
        return discount;
    }
}
