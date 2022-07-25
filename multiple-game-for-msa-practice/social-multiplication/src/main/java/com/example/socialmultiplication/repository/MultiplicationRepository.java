package com.example.socialmultiplication.repository;

import com.example.socialmultiplication.domain.Multiplication;
import org.springframework.data.repository.CrudRepository;

public interface MultiplicationRepository extends CrudRepository<Multiplication, Long> {
}
