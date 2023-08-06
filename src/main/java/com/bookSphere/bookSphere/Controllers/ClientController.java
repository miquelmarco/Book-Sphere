package com.bookSphere.bookSphere.Controllers;
import com.bookSphere.bookSphere.dto.ClientDTO;
import com.bookSphere.bookSphere.models.BuyOrder;
import com.bookSphere.bookSphere.models.Client;
import com.bookSphere.bookSphere.models.OrderStatus;
import com.bookSphere.bookSphere.repositories.BuyOrderRepository;
import com.bookSphere.bookSphere.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    BuyOrderRepository buyOrderRepository;

    @GetMapping("/clients/current")
    ClientDTO clientDTO(Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());
        ClientDTO clientDTO=new ClientDTO(client);
        return clientDTO ;
    }
    @GetMapping("/clients")
    List<ClientDTO> getClientsDTO(){
        List<ClientDTO> clientsDTO=clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());
        return clientsDTO;
    }

    @PostMapping("/clients/register")
    ResponseEntity<Object> registerClient(@RequestParam String firstName,@RequestParam String lastName,@RequestParam String email,@RequestParam String password){
        if (password.isBlank()) {
            return new ResponseEntity<>("Missing password.", HttpStatus.FORBIDDEN);
        }
        if(firstName.isBlank()){
            return new ResponseEntity<>("Missing First Name.", HttpStatus.FORBIDDEN);
        }
        if(email.isBlank()){
            return new ResponseEntity<>("Missing e-mail.", HttpStatus.FORBIDDEN);
        }
        if(lastName.isBlank()){
            return new ResponseEntity<>("Missing last name.", HttpStatus.FORBIDDEN);
        }
        if(clientRepository.findByEmail(email)!=null){
            return new ResponseEntity<>("User already exists.", HttpStatus.FORBIDDEN);
        }
        Client client=new Client(firstName,lastName,email,passwordEncoder.encode(password),false);
        clientRepository.save(client);
        return new ResponseEntity<>("Client successfully created",HttpStatus.CREATED);
    }
    @PostMapping("/clients/register/admin")
    ResponseEntity<Object> createAccountAdmin(@RequestParam String firstName,@RequestParam String lastName,@RequestParam String email,@RequestParam String password,@RequestParam boolean isAdmin){
        if (password.isBlank()) {
            return new ResponseEntity<>("Missing password.", HttpStatus.FORBIDDEN);
        }
        if(firstName.isBlank()){
            return new ResponseEntity<>("Missing First Name.", HttpStatus.FORBIDDEN);
        }
        if(email.isBlank()){
            return new ResponseEntity<>("Missing e-mail.", HttpStatus.FORBIDDEN);
        }
        if(lastName.isBlank()){
            return new ResponseEntity<>("Missing last name.", HttpStatus.FORBIDDEN);
        }
        if(clientRepository.findByEmail(email)!=null){
            return new ResponseEntity<>("User already exists.", HttpStatus.FORBIDDEN);
        }
        Client client=new Client(firstName,lastName,email,passwordEncoder.encode(password),isAdmin);
        clientRepository.save(client);
        return new ResponseEntity<>("Client successfully created",HttpStatus.CREATED);
    }
    @PutMapping("/clients/delete")
    public ResponseEntity<Object> deleteClient(@RequestParam Long id){
        Client client=clientRepository.findById(id).orElse(null);
        client.setDeleted(true);
        clientRepository.save(client);
        return new ResponseEntity<>("Client successfully deleted",HttpStatus.OK);
    }
    @PutMapping ("/clients/address")
    public  ResponseEntity <Object> setAddress (@RequestParam String shippingAdress, Authentication authentication){
        if (shippingAdress.isBlank()){
            return new ResponseEntity<>("Missing shipping address", HttpStatus.FORBIDDEN);
        }
        Client client = clientRepository.findByEmail(authentication.getName());
        client.setShippingAdress(shippingAdress);
        clientRepository.save(client);

        return new ResponseEntity<>("Shipping address correctly modified", HttpStatus.CREATED);
    }
    @PutMapping("/clients/orderStatus")
    public ResponseEntity<Object> setOrderStatus(@RequestParam Long id, @RequestParam OrderStatus orderStatus){
        BuyOrder buyOrder=buyOrderRepository.findById(id).orElse(null);
        if(buyOrder==null){
            return new ResponseEntity<>("Incorrect ID", HttpStatus.FORBIDDEN);
        }
        if(orderStatus==null){
            return new ResponseEntity<>("Select a valid status", HttpStatus.FORBIDDEN);
        }
        buyOrder.setStatus(orderStatus);
        buyOrderRepository.save(buyOrder);
        return new ResponseEntity<>("Order status successfully modified", HttpStatus.OK);
    }

    @PutMapping("/buyOrder/delete")
    ResponseEntity<Object> deleteBuyOrder(@RequestParam Long id){
        BuyOrder buyOrder=buyOrderRepository.findById(id).orElse(null);
        buyOrder.setDeleted(true);
        buyOrderRepository.save(buyOrder);
        return new ResponseEntity<>("Buy order successfully deleted", HttpStatus.OK);
    }
}