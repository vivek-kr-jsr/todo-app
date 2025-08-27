package com.example.ProjectDemo.repository;

import com.example.ProjectDemo.model.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    // Search by title containing keyword (case-insensitive) with pagination
    Page<Todo> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);
}
