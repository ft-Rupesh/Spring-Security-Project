package com.globex.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.globex.model.mapper.ModelMapperSingleton;
import com.globex.pojo.TodoDeleteResponse;
import com.globex.pojo.TodoRequestModel;
import com.globex.pojo.TodoResponse;
import com.globex.service.TodoService;
import com.globex.userdto.TodoDto;

@RestController
@RequestMapping("/todo")
public class TodoController {
    @Autowired
    private TodoService todoService;

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
//    @PostAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/addTodo")
    public ResponseEntity<TodoResponse> createTodo(@Validated @RequestBody TodoRequestModel todoRequestModel) {
        ModelMapper mapper = ModelMapperSingleton.getInstance();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        TodoDto todoDto = mapper.map(todoRequestModel, TodoDto.class);
        todoService.createTodo(todoDto);
        TodoResponse response = mapper.map(todoDto, TodoResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }


    @GetMapping("/getAllTodo")
    public ResponseEntity<List<TodoDto>> getAllTodo() {
        return ResponseEntity.status(HttpStatus.FOUND).body(todoService.getAllTodo());
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/delete/{todoId}")
    public ResponseEntity<TodoDeleteResponse> deleteTodoById(@PathVariable String todoId) {
        TodoDto todoDto = todoService.deleteTodoById(todoId);
        TodoDeleteResponse response = ModelMapperSingleton.getInstance().map(todoDto, TodoDeleteResponse.class);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PutMapping("/update/{todoId}")
    public ResponseEntity<TodoResponse> updateTodo(@PathVariable String todoId,@Validated @RequestBody TodoRequestModel todoRequestModel){
        ModelMapper mapper = ModelMapperSingleton.getInstance();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        TodoDto todoDto=mapper.map(todoRequestModel,TodoDto.class);

        TodoDto updatedDto=todoService.updateTodo(todoId,todoDto);

        TodoResponse response=mapper.map(updatedDto,TodoResponse.class);
        return  ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
