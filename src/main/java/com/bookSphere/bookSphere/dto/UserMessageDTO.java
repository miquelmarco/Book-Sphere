package com.bookSphere.bookSphere.dto;

import com.bookSphere.bookSphere.models.UserMessage;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserMessageDTO {
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String message;
    private boolean isDeleted;
    private LocalDate localDate;

    public UserMessageDTO() {
    }

    public UserMessageDTO(UserMessage userMessage) {
        id=userMessage.getId();
        fullName = userMessage.getFullName();
        email = userMessage.getEmail();
        phoneNumber= userMessage.getPhoneNumber();
        message = userMessage.getMessage();
        isDeleted= userMessage.isDeleted();
        localDate=userMessage.getLocalDate();
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getMessage() {
        return message;
    }
}
