package com.example.todolist.repository;

import com.example.todolist.model.Project;
import com.example.todolist.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo,Integer> {
    List<Todo> findByProjectId(Integer projectId);
    List<Todo> findByProjectIdAndStatus(Integer projectId,String status);
}
