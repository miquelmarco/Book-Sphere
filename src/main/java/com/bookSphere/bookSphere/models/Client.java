package com.bookSphere.bookSphere.models;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName, lastName, email, password;
    @OneToMany(mappedBy = "client")
    private Set<BuyOrder> buyOrders=new HashSet<>();
    @OneToMany(mappedBy = "client")
    private Set<Reservation> reservations=new HashSet<>();
    public Client() {
    }
    boolean isAdmin;
    boolean isDeleted;
    private String shippingAdress;

    public Client(String firstName, String lastName, String email, String password,boolean isAdmin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.isAdmin=isAdmin;
    }

    public String getShippingAdress() {
        return shippingAdress;
    }

    public void setShippingAdress(String shippingAdress) {
        this.shippingAdress = shippingAdress;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public Long getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void addReservation(Reservation reservation){
        reservation.setClient(this);
        reservations.add(reservation);
    }
    public void addBuyOrder(BuyOrder buyOrder){
        buyOrder.setClient(this);
        buyOrders.add(buyOrder);
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Set<BuyOrder> getBuyOrders() {
        return buyOrders;
    }

    public void setBuyOrders(Set<BuyOrder> buyOrders) {
        this.buyOrders = buyOrders;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }
}
