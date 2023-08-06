package com.bookSphere.bookSphere.models;
import javax.persistence.*;
import java.util.Set;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String isbn, title, author, category, img;
    @Column(length = 1000)
    private String description;
    private Integer stock;
    private Double price;
    private Integer discount;
    boolean isDeleted;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private Set<OrderBuy> orderBuys;
    public Book() {
    }
    public Book(String isbn, String title, String author, String category, String description, String img, Integer stock, Double price, Integer discount,boolean isDeleted) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.category = category;
        this.description = description;
        this.img = img;
        this.stock = stock;
        this.price = price;
        this.discount = discount;
        this.isDeleted=isDeleted;
    }

    public Set<OrderBuy> getOrderBuys() {
        return orderBuys;
    }

    public void setOrderBuys(Set<OrderBuy> orderBuys) {
        this.orderBuys = orderBuys;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAutor() {
        return author;
    }
    public void setAutor(String autor) {
        this.author = autor;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
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
    public Integer getStock() {
        return stock;
    }
    public void setStock(Integer stock) {
        this.stock = stock;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }
}