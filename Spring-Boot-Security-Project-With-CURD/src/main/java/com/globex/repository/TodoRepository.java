package com.globex.repository;

import org.springframework.data.repository.CrudRepository;

import com.globex.pojo.Todo;

public interface TodoRepository extends CrudRepository<Todo,Long> {
    Todo findByTodoId(String todoId);
}
