package com.bookSphere.bookSphere.dto;

import com.bookSphere.bookSphere.models.BuyOrder;
import com.bookSphere.bookSphere.models.Client;
import com.bookSphere.bookSphere.models.Reservation;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {
    private String firstName, lastName, email;

    private Set<BuyOrderDTO> buyOrders;

    private boolean isAdmin;
    private boolean isDeleted;
    private Long id;
    private String shippingAdress;

    private Set<Reservation> reservations;

    public ClientDTO() {
    }

    public ClientDTO(Client client) {
        firstName = client.getFirstName();
        lastName = client.getLastName();
        email = client.getEmail();
        buyOrders = client.getBuyOrders().stream().map(buyOrder -> new BuyOrderDTO(buyOrder)).collect(Collectors.toSet());
        isAdmin= client.isAdmin();
        id=client.getId();
        isDeleted= client.isDeleted();
        shippingAdress= client.getShippingAdress();
        reservations = client.getReservations();

    }

    public String getShippingAdress() {
        return shippingAdress;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public Long getId() {
        return id;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Set<BuyOrderDTO> getBuyOrders() {
        return buyOrders;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }
}
