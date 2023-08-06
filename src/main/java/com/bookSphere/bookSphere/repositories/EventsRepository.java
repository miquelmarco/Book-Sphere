package com.bookSphere.bookSphere.repositories;
import com.bookSphere.bookSphere.models.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface EventsRepository extends JpaRepository<Events, Long> {
}
