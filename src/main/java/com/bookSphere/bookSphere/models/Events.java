package com.bookSphere.bookSphere.models;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
@Entity
public class Events {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String name, img;
    @Column(length = 1000)
    public String description;
    public Integer capacity;
    public LocalTime time;
    public LocalDate date;
    public boolean isDeleted;
    public EventRoom eventRoom;

    public Events(String name, String img, String description, Integer capacity, LocalTime time, LocalDate date, boolean isDeleted,EventRoom eventRoom) {
        this.name = name;
        this.img = img;
        this.description = description;
        this.capacity = capacity;
        this.time = time;
        this.date = date;
        this.isDeleted = isDeleted;
        this.eventRoom=eventRoom;
    }

    public Events() {
    }

    public EventRoom getEventRoom() {
        return eventRoom;
    }

    public void setEventRoom(EventRoom eventRoom) {
        this.eventRoom = eventRoom;
    }

    public void setId(Long id) {
        this.id = id;
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getImg() {
        return img;
    }
    public void setImg(String img) {
        this.img = img;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getCapacity() {
        return capacity;
    }
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
    public LocalTime getTime() {
        return time;
    }
    public void setTime(LocalTime time) {
        this.time = time;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
}