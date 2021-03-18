package com.example.todolist.response;

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
public class AddProjectResponse {

    private Integer id;
    private String projectName;
    private Integer noOfTodos;
    private Integer completedTodos;
    private Timestamp createdAt;

    public static AddProjectResponse from(Project project) {

        AddProjectResponse addProjectResponse=AddProjectResponse.builder()
                .projectName(project.getProjectName())
                .noOfTodos(project.getNoOfTodos())
                .completedTodos(project.getCompletedTodos())
                .createdAt(project.getCreatedAt())
                .build();
        return addProjectResponse;
    }


}
