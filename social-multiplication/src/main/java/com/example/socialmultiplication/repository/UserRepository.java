package com.example.socialmultiplication.repository;

import com.example.socialmultiplication.domain.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<Users, Long> {

    Optional<Users> findByAlias(final String alias);
}
