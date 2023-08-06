package com.bookSphere.bookSphere.repositories;

import com.bookSphere.bookSphere.models.UserMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UserMessageRepository extends JpaRepository<UserMessage,Long> {
}
