package com.bookSphere.bookSphere.repositories;
import com.bookSphere.bookSphere.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
@RepositoryRestResource
public interface BookRepository extends JpaRepository<Book, Long> {
}