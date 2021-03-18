package com.example.todolist.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue
    private Integer id;
    private String projectName;
    private Integer noOfTodos;
    private Integer completedTodos;
    private Timestamp createdAt;

    private String accessToken;

    private String userId;

//    @OneToMany(mappedBy="project")
//    private List<Todo> todos;


}
