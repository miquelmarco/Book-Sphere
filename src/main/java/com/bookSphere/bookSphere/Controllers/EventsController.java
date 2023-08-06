package com.bookSphere.bookSphere.Controllers;
import com.bookSphere.bookSphere.dto.EventDTO;
import com.bookSphere.bookSphere.models.Events;
import com.bookSphere.bookSphere.repositories.EventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class EventsController {
    @Autowired
    EventsRepository eventsRepository;

    @GetMapping("/events")
    List<EventDTO> getEventsDTO(){
        List<EventDTO> events=eventsRepository.findAll().stream().map(event->new EventDTO(event)).collect(Collectors.toList());
        return events;
    }
    @PostMapping("/events/create")
   public ResponseEntity<Object> createEvent(@RequestBody EventDTO eventDTO){
        if(eventDTO.getName().isBlank()){
            return new ResponseEntity<>("Please enter a name ",HttpStatus.FORBIDDEN);
        }
        if(eventDTO.getDescription().isBlank()){
            return new ResponseEntity<>("Please enter a description",HttpStatus.FORBIDDEN);
        }
        if(eventDTO.getImg().isBlank()){
            return new ResponseEntity<>("Please enter an image ",HttpStatus.FORBIDDEN);
        }
        Events event= new Events(eventDTO.getName(),eventDTO.getImg(),eventDTO.getDescription(),eventDTO.getCapacity(),eventDTO.getTime(),eventDTO.getDate(),false,eventDTO.getEventRoom());
        eventsRepository.save(event);
        return new ResponseEntity<>("Event created successfully", HttpStatus.CREATED);
    }
    @PutMapping("/events/delete")
    public ResponseEntity<Object> deleteEvent(@RequestParam Long id){
        Events event=eventsRepository.findById(id).orElse(null);
        event.setDeleted(true);
        eventsRepository.save(event);
        return new ResponseEntity<>("Event successfully deleted",HttpStatus.OK);
    }
}
