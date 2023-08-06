package com.bookSphere.bookSphere.repositories;
import com.bookSphere.bookSphere.models.FeaturedAuthor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
@RepositoryRestResource
public interface FeaturedAuthorRepository extends JpaRepository<FeaturedAuthor, Long> {
}