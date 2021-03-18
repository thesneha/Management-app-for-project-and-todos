package com.example.todolist.request;

import com.example.todolist.model.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectRequest {
    private String projectName;
    private Integer id;
    private String userId;


    public static Project to(ProjectRequest projectRequest) {

        Project project = Project.builder()
                .projectName(projectRequest.getProjectName())
                .noOfTodos(0)
                .userId(projectRequest.userId)
                .completedTodos(0)
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .build();
        return project;
    }
}

