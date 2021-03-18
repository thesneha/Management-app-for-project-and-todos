package com.example.todolist.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
    @Id
    @GeneratedValue
    private Integer id;
    private String description;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    private Integer projectId;



}

//
//    @ManyToOne
//    @JoinColumn(name="project_id", nullable=false)
//    private Project project;


