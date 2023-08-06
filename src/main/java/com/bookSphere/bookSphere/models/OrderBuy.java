package com.bookSphere.bookSphere.models;
import javax.persistence.*;

@Entity
public class OrderBuy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "buy_order_id")
    private BuyOrder buyOrder;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    private double amount;

    public OrderBuy() {
    }

    public OrderBuy(int quantity,Book book,double amount) {
        this.quantity = quantity;
        this.book = book;
        this.amount= amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BuyOrder getBuyOrder() {
        return buyOrder;
    }

    public void setBuyOrder(BuyOrder buyOrder) {
        this.buyOrder = buyOrder;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
