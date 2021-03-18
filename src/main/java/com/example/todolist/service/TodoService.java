package com.example.todolist.service;

import com.example.todolist.model.Project;
import com.example.todolist.model.Todo;
import com.example.todolist.model.User;
import com.example.todolist.request.TodoRequest;

import java.util.List;

public interface TodoService {
    List<Todo> getPendingTodosByProjectId(Integer projectId) throws Exception;

    List<Todo> getCompletedTodosByProjectId(Integer projectId) throws Exception;

    List<Todo> getTodosByProjectId(Integer projectId) throws Exception;

    Todo addTodo(TodoRequest todoRequest ) throws Exception;

    Todo getTodoById(Integer todoId) throws Exception;

    void deleteTodo(Integer id);

    void markCompleted(Todo todo);

    String exportToGist(Project project, User user) throws Exception;
}
