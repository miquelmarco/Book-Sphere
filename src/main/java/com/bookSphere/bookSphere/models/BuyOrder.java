package com.bookSphere.bookSphere.models;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class BuyOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public Double amount;
    public String orderNumber;
    public LocalDate date;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;
    @OneToMany(mappedBy = "buyOrder", cascade = CascadeType.ALL)
    private Set<OrderBuy> orderBuys=new HashSet<>();
    private OrderStatus status;

    private Boolean isDeleted;

    public BuyOrder() {
    }
    public BuyOrder(String orderNumber, Double amount, LocalDate date,Client client, OrderStatus status, Boolean isDeleted) {
        this.orderNumber = orderNumber;
        this.amount = amount;
        this.date = date;
        this.client=client;
        this.status = status;
        this.isDeleted=isDeleted;
    }

    public Set<OrderBuy> getOrderBuys() {
        return orderBuys;
    }

    public void setOrderBuys(Set<OrderBuy> orderBuys) {
        this.orderBuys = orderBuys;
    }

    public Long getId() {
        return id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}