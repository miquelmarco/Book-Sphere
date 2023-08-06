package com.bookSphere.bookSphere.dto;

import com.bookSphere.bookSphere.models.EventRoom;
import com.bookSphere.bookSphere.models.Events;
import com.fasterxml.jackson.annotation.JsonFormat;
import jdk.jfr.Event;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventDTO {
    private String name, img, description;
    private Integer capacity;
    private LocalTime time;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate date;
    private EventRoom eventRoom;
    private Long id;
    private boolean isDeleted;

    public EventDTO() {
    }

    public EventDTO(Events events) {
        name = events.getName();
        img = events.getImg();
        description = events.getDescription();
       capacity = events.getCapacity();
        time = events.getTime();
        date = events.getDate();
        eventRoom=events.getEventRoom();
        id= events.getId();
        isDeleted= events.isDeleted();
    }

    public EventRoom getEventRoom() {
        return eventRoom;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }

    public String getDescription() {
        return description;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public LocalTime getTime() {
        return time;
    }

    public LocalDate getDate() {
        return date;
    }

}
