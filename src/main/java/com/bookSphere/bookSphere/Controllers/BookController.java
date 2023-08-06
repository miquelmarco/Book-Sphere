package com.bookSphere.bookSphere.Controllers;
import com.bookSphere.bookSphere.dto.BookDTO;
import com.bookSphere.bookSphere.models.Book;
import com.bookSphere.bookSphere.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class BookController {
    @Autowired
    BookRepository bookRepository;
 @GetMapping("/books")
 List<BookDTO> getBooksDTO(){
     List<Book> nonDeletedBooks=bookRepository.findAll().stream().filter(book -> book.isDeleted()==false).collect(Collectors.toList());
         List<BookDTO> booksDTO=nonDeletedBooks.stream().map(book->new BookDTO(book)).collect(Collectors.toList());
         return booksDTO;
     }
     @PostMapping("/books/create")
     ResponseEntity<Object> createBooks(@RequestBody BookDTO bookDTO){
        if(bookDTO.getAuthor().isBlank()){
            return new ResponseEntity<>("Please enter a name",HttpStatus.FORBIDDEN);
         }
         if(bookDTO.getIsbn().isBlank()){
             return new ResponseEntity<>("Please enter a ISBN",HttpStatus.FORBIDDEN);
         }
         if(bookDTO.getCategory().isBlank()){
             return new ResponseEntity<>("Please enter a category",HttpStatus.FORBIDDEN);
         }
         if(bookDTO.getAuthor().isBlank()){
             return new ResponseEntity<>("Please enter an author",HttpStatus.FORBIDDEN);
         }
         if(bookDTO.getImg().isBlank()){
             return new ResponseEntity<>("Please enter an image",HttpStatus.FORBIDDEN);
         }
         if(bookDTO.getDiscount()<1){
             return new ResponseEntity<>("Please enter valid discount",HttpStatus.FORBIDDEN);
         }
         if(bookDTO.getPrice()<0){
             return new ResponseEntity<>("Please enter a valid price",HttpStatus.FORBIDDEN);
         }
         if(bookDTO.getStock()<1){
             return new ResponseEntity<>("Please enter a stock",HttpStatus.FORBIDDEN);
         }

      Book book=new Book(bookDTO.getIsbn(), bookDTO.getTitle(),bookDTO.getAuthor(), bookDTO.getCategory(), bookDTO.getDescription(), bookDTO.getImg(), bookDTO.getStock(), bookDTO.getPrice(), bookDTO.getStock(), false);
         bookRepository.save(book);
      return new ResponseEntity<>("Book created successfully", HttpStatus.CREATED);
     }
     @PutMapping("/books/delete")
    ResponseEntity<Object> deleteBook(@RequestParam Long id){
     Book book=bookRepository.findById(id).orElse(null);
     book.setDeleted(true);
     bookRepository.save(book);
     return new ResponseEntity<>("Book successfully deleted",HttpStatus.OK);
     }
}
