package com.globex.service;

import java.util.List;

import com.globex.userdto.TodoDto;

public interface TodoService {
    public List<TodoDto> getAllTodo();
    public TodoDto createTodo(TodoDto todoDto);
    public TodoDto updateTodo(String todoId, TodoDto todo);
    public TodoDto deleteTodoById(String todoId);


}
