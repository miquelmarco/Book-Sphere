package com.bookSphere.bookSphere.models;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public LocalDate date;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    public Client client;

    public EventRoom eventRoom;
    private Boolean isDeleted;
    public Reservation() {
    }
    public Reservation(LocalDate date,EventRoom eventRoom, Boolean isDeleted) {
        this.date = date;
        this.eventRoom=eventRoom;
        this.isDeleted=isDeleted;

    }
    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    @JsonIgnore
    public Client getClient() {
        return client;
    }

    public EventRoom getEventRoom() {
        return eventRoom;
    }

    public void setEventRoom(EventRoom eventRoom) {
        this.eventRoom = eventRoom;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}