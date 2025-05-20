package com.globex.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globex.model.mapper.ModelMapperSingleton;
import com.globex.pojo.Todo;
import com.globex.repository.TodoRepository;
import com.globex.userdto.TodoDto;

import jakarta.transaction.Transactional;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Override
    public List<TodoDto> getAllTodo() {
        ModelMapper mapper = ModelMapperSingleton.getInstance();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        List<Todo> todos=(List<Todo>)todoRepository.findAll();
        return todos.stream().map(todo->
                mapper.map(todo,TodoDto.class)).collect(Collectors.toList());
    }

    @Override
    public TodoDto createTodo(TodoDto todoDto) {
        todoDto.setTodoId(UUID.randomUUID().toString());
        ModelMapper mapper = ModelMapperSingleton.getInstance();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Todo todo=mapper.map(todoDto,Todo.class);
        todoRepository.save(todo);

        TodoDto returnDto=mapper.map(todo,TodoDto.class);
        return  returnDto;

    }

    @Override
    public TodoDto updateTodo(String todoId,TodoDto todo) {
          Todo temp_todo=todoRepository.findByTodoId(todoId);
if(temp_todo==null){
    throw new RuntimeException("Wrong Todo Id Entered"+todoId);
}

        temp_todo.setName(todo.getName());
        temp_todo.setDescription(todo.getDescription());
        temp_todo.setPrice(todo.getPrice());
        temp_todo.setManufactureTime(todo.getManufactureTime());
        temp_todo.setExpTime(todo.getExpTime());
           todoRepository.save(temp_todo);
        TodoDto todoDto=ModelMapperSingleton.getInstance().map(temp_todo,TodoDto.class);
        return todoDto;
    }
    
    @Transactional
    @Override
    public TodoDto deleteTodoById(String todoId) {
        Todo todo=todoRepository.findByTodoId(todoId);
        todoRepository.delete(todo);
        return ModelMapperSingleton.getInstance().map(todo,TodoDto.class);
    }
}
