package com.example.todolist.service.impl;

import com.example.todolist.model.Project;
import com.example.todolist.repository.ProjectRepository;
import com.example.todolist.request.ProjectRequest;
import com.example.todolist.response.AddProjectResponse;
import com.example.todolist.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImp implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Override
    public AddProjectResponse addProject(ProjectRequest projectRequest) throws Exception {
        //todo project name validation
        if(StringUtils.isEmpty(projectRequest.getProjectName())){
            throw new Exception("project Name is empty");
        }

        Project project= ProjectRequest.to(projectRequest);

        Project project1= projectRepository.save(project);
       // System.out.println(project1);
        return AddProjectResponse.from(project1);

    }

    @Override
    public List<Project> getProjectByUserId(String userId) throws Exception {
        if(StringUtils.isEmpty(userId)){
            throw new Exception("Invalid user id");
        }
        List<Project> projects= projectRepository.findByUserId(userId);
        return projects;
    }

    @Override
    public void deleteProject(ProjectRequest projectRequest) throws Exception {
        if (projectRequest.getId() == null) {
            throw new Exception("Invalid project id");
        }
        projectRepository.deleteById(projectRequest.getId());
    }

    @Override
    public Project getByProjectId(Integer projectId) throws Exception {
         Optional<Project> project= projectRepository.findById(projectId);
         if (project.isPresent()){
             return project.get();
         }
         else {
             throw new Exception("Project with project id is not present");
         }
    }

    @Override
    public Project incrementCompleted(Project project) {
        int completed = project.getCompletedTodos();
        project.setCompletedTodos(completed+1);
        return projectRepository.save(project);
    }

    @Override
    public Project incrementTodos(Project project) {
        int todos = project.getNoOfTodos();
        project.setNoOfTodos(todos+1);
        return projectRepository.save(project);
    }
}
