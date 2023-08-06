package com.bookSphere.bookSphere.dto;

import com.bookSphere.bookSphere.models.BuyOrder;
import com.bookSphere.bookSphere.models.OrderStatus;

import java.time.LocalDate;

public class BuyOrderDTO {
    private Long id;
    private Double amount;
    private String orderNumber;
    private LocalDate date;
    private OrderStatus status;
    private String clientName;
    private Boolean isDeleted;

    public BuyOrderDTO() {
    }

    public BuyOrderDTO(BuyOrder buyOrder) {
        id= buyOrder.getId();
        amount = buyOrder.getAmount();
        orderNumber = buyOrder.getOrderNumber();
        date = buyOrder.getDate();
        status = buyOrder.getStatus();
        clientName=buyOrder.getClient().getFirstName()+" "+buyOrder.getClient().getLastName();
        isDeleted=buyOrder.getDeleted();
    }



    public String getClientName() {
        return clientName;
    }

    public Long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public LocalDate getDate() {
        return date;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }
}
