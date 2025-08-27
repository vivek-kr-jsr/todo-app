package com.example.ProjectDemo.controller;

import com.example.ProjectDemo.dto.TodoRequestDto;
import com.example.ProjectDemo.dto.TodoResponseDto;
import com.example.ProjectDemo.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    // Get all todos
    @GetMapping("/v1/all")
    public ResponseEntity<List<TodoResponseDto>> getAllTodoList() {
        return ResponseEntity.ok(todoService.getTask());
    }

    // Add a todo
    @PostMapping("/v1/add")
    public ResponseEntity<TodoResponseDto> addTask(@Valid @RequestBody TodoRequestDto requestDto) {
        return ResponseEntity.ok(todoService.add(requestDto));
    }

    // Update a todo
    @PutMapping("/v1/update/{id}")
    public ResponseEntity<TodoResponseDto> updateTask(@PathVariable Long id,
                                                      @Valid @RequestBody TodoRequestDto requestDto) {
        return ResponseEntity.ok(todoService.updateTodo(id, requestDto));
    }

    // Delete a todo
    @DeleteMapping("/v1/delete/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        todoService.removeTask(id);
        return ResponseEntity.ok("Todo deleted successfully with id: " + id);
    }

    // Search todos
    @GetMapping("/v1/search")
    public ResponseEntity<Page<TodoResponseDto>> searchTodos(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<TodoResponseDto> result = todoService.getSearchByTitle(keyword, pageable);

        return ResponseEntity.ok(result);
    }
}
