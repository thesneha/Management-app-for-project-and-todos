package com.example.todolist.request;

import com.example.todolist.model.Project;
import com.example.todolist.model.Todo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoRequest {
    private String description;
    private Integer projectId;
    private String userId;
    private Integer id;
    private String status;

    public static Todo to(TodoRequest todoRequest) {
        Todo todo = Todo.builder()
                .description(todoRequest.getDescription())
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .updatedAt(new Timestamp(System.currentTimeMillis()))
                .status("pending")
                .projectId(todoRequest.getProjectId())
                .build();

        return todo;
    }

}
