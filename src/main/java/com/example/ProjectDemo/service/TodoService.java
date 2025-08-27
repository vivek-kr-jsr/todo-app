package com.example.ProjectDemo.service;

import com.example.ProjectDemo.dto.TodoRequestDto;
import com.example.ProjectDemo.dto.TodoResponseDto;
import com.example.ProjectDemo.model.Todo;
import com.example.ProjectDemo.repository.TodoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoService {

    private final TodoRepository repository;

    @Autowired
    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    // Get All
    public List<TodoResponseDto> getTask() {
        return repository.findAll()
                .stream()
                .map(todo -> new TodoResponseDto(todo.getId(), todo.getTitle(), todo.getStatus()))
                .collect(Collectors.toList());
    }

    // Add
    public TodoResponseDto add(TodoRequestDto requestDto) {
        Todo todo = new Todo();
        todo.setTitle(requestDto.getTitle());
        todo.setStatus(requestDto.getStatus());
        Todo saved = repository.save(todo);
        return new TodoResponseDto(saved.getId(), saved.getTitle(), saved.getStatus());
    }

    // Update
    public TodoResponseDto updateTodo(Long id, TodoRequestDto requestDto) {
        return repository.findById(id)
                .map(todo -> {
                    todo.setTitle(requestDto.getTitle());
                    todo.setStatus(requestDto.getStatus());
                    Todo updated = repository.save(todo);
                    return new TodoResponseDto(updated.getId(), updated.getTitle(), updated.getStatus());
                })
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));
    }

    // Delete
    public void removeTask(Long id) {
        repository.deleteById(id);
    }

    // Search by keyword 
    public Page<TodoResponseDto> getSearchByTitle(String keyword, Pageable pageable) {
        return repository.findByTitleContainingIgnoreCase(keyword, pageable)
                .map(todo -> new TodoResponseDto(todo.getId(), todo.getTitle(), todo.getStatus()));
    }
}
