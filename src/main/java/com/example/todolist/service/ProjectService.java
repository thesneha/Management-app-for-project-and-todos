package com.example.todolist.service;

import com.example.todolist.model.Project;
import com.example.todolist.request.ProjectRequest;
import com.example.todolist.response.AddProjectResponse;

import java.util.List;

public interface ProjectService {


    AddProjectResponse addProject(ProjectRequest projectRequest) throws Exception;

    List<Project> getProjectByUserId(String userId) throws Exception;

    void deleteProject(ProjectRequest projectRequest) throws Exception;

    Project getByProjectId(Integer projectId) throws Exception;

    Project incrementCompleted(Project project);

    Project incrementTodos(Project project);
}
