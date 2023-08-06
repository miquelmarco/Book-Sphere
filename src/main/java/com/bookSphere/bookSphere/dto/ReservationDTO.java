package com.bookSphere.bookSphere.dto;

import com.bookSphere.bookSphere.models.EventRoom;
import com.bookSphere.bookSphere.models.Reservation;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class ReservationDTO {
    public Long id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate date;
    public EventRoom eventRoom;
    private Boolean isDeleted;
    private String client;
    public ReservationDTO() {
    }
    public ReservationDTO(Reservation reservation) {
        id = reservation.getId();
        date = reservation.getDate();
        eventRoom=reservation.getEventRoom();
        isDeleted=reservation.getDeleted();
        client=reservation.getClient().getFirstName()+" " + reservation.getClient().getLastName();
    }

    public Long getId() {
        return id;
    }

    public EventRoom getEventRoom() {
        return eventRoom;
    }

    public LocalDate getDate() {
        return date;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public String getClient() {
        return client;
    }
}
