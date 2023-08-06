package com.bookSphere.bookSphere.Controllers;
import com.bookSphere.bookSphere.dto.FeaturedAuthorDTO;
import com.bookSphere.bookSphere.models.FeaturedAuthor;
import com.bookSphere.bookSphere.repositories.FeaturedAuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class FeaturedAuthorController {
    @Autowired
    FeaturedAuthorRepository featuredAuthorRepository;

    @GetMapping("/authors")
    public List<FeaturedAuthorDTO> getFeaturedAuthorsDTO(){
        List<FeaturedAuthorDTO> authors=featuredAuthorRepository.findAll().stream().map(author->new FeaturedAuthorDTO(author)).collect(Collectors.toList());
        return authors;
    }
    @PostMapping("/authors/create")
   public ResponseEntity<Object> createFeaturedAuthor(@RequestBody FeaturedAuthorDTO featuredAuthorDTO){
        if(featuredAuthorDTO.getFirstName().isBlank()){
            return new ResponseEntity<>("Please enter a name ",HttpStatus.FORBIDDEN);
        }
        if(featuredAuthorDTO.getLastName().isBlank()){
            return new ResponseEntity<>("Please enter a last name ",HttpStatus.FORBIDDEN);
        }
        if(featuredAuthorDTO.getDescription().isBlank()){
            return new ResponseEntity<>("Please enter a description ",HttpStatus.FORBIDDEN);
        }
        if(featuredAuthorDTO.getGenre().isBlank()){
            return new ResponseEntity<>("Please enter a gender ",HttpStatus.FORBIDDEN);
        }
        if(featuredAuthorDTO.getNationality().isBlank()){
            return new ResponseEntity<>("Please enter nationality ",HttpStatus.FORBIDDEN);
        }
        if(featuredAuthorDTO.getImg().isBlank()){
            return new ResponseEntity<>("Please enter an image ",HttpStatus.FORBIDDEN);
        }
        FeaturedAuthor featuredAuthor=new FeaturedAuthor(featuredAuthorDTO.getFirstName(),featuredAuthorDTO.getLastName(),featuredAuthorDTO.getNationality(),featuredAuthorDTO.getGenre(),featuredAuthorDTO.getDescription(),featuredAuthorDTO.getImg(),false);
        featuredAuthorRepository.save(featuredAuthor);
        return new ResponseEntity<>("Author created successfull",HttpStatus.CREATED);

    }
    @PutMapping("/authors/delete")
    public ResponseEntity<Object> deleteAuthor(@RequestParam Long id ){
        FeaturedAuthor featuredAuthor=featuredAuthorRepository.findById(id).orElse(null);
        featuredAuthor.setDeleted(true);
        featuredAuthorRepository.save(featuredAuthor);
        return new ResponseEntity<>("author deleted successfully", HttpStatus.OK);
    }
}
