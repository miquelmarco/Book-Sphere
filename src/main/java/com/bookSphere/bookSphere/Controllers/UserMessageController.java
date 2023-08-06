package com.bookSphere.bookSphere.Controllers;
import com.bookSphere.bookSphere.dto.UserMessageDTO;
import com.bookSphere.bookSphere.models.UserMessage;
import com.bookSphere.bookSphere.repositories.UserMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserMessageController {
    @Autowired
    UserMessageRepository userMessageRepository;
    @PostMapping("/message/create")
    ResponseEntity<Object> createUserMessage(@RequestBody UserMessageDTO userMessageDTO){
        if(userMessageDTO.getFullName().isBlank()){
            return new ResponseEntity<>("Please enter yoUr full name.", HttpStatus.FORBIDDEN);
        }
        if(userMessageDTO.getEmail().isBlank()){
            return new ResponseEntity<>("Please enter yor email.", HttpStatus.FORBIDDEN);
        }
        if(userMessageDTO.getPhoneNumber().isBlank()){
            return new ResponseEntity<>("Please enter yor phone number.", HttpStatus.FORBIDDEN);
        }
        if(userMessageDTO.getMessage().isBlank()){
            return new ResponseEntity<>("Please enter a message.", HttpStatus.FORBIDDEN);
        }
        UserMessage userMessage=new UserMessage(userMessageDTO.getFullName(), userMessageDTO.getEmail(), userMessageDTO.getPhoneNumber(), userMessageDTO.getMessage(), LocalDate.now());
        userMessage.setDeleted(false);
        userMessageRepository.save(userMessage);
        return new ResponseEntity<>("UserMessage created sucessfully.",HttpStatus.CREATED);

    }
    @GetMapping("/message")
    List<UserMessageDTO> getUserMessagesDTO(){
        List<UserMessageDTO> userMessagesDTO=userMessageRepository.findAll().stream().map(userMessage -> new UserMessageDTO(userMessage)).collect(Collectors.toList());
        return userMessagesDTO;
    }
    @PutMapping("/message/delete")
    ResponseEntity<Object> deleteUserMessage(@RequestParam Long id){
        UserMessage userMessage=userMessageRepository.findById(id).orElse(null);
        if(userMessage==null){
            return  new ResponseEntity<>("Invalid id",HttpStatus.FORBIDDEN);
        }
        userMessage.setDeleted(true);
        userMessageRepository.save(userMessage);
        return new ResponseEntity<>("Message deleted successfully",HttpStatus.OK);
    }

}
