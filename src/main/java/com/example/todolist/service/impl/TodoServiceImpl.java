package com.example.todolist.service.impl;

import com.example.todolist.model.Project;
import com.example.todolist.model.Todo;
import com.example.todolist.model.User;
import com.example.todolist.repository.TodoRepository;
import com.example.todolist.request.Content;
import com.example.todolist.request.ExportGistRequest;
import com.example.todolist.request.TodoRequest;
import com.example.todolist.response.AddToGistResponse;
import com.example.todolist.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    TodoRepository todoRepository;

    @Override
    public List<Todo> getPendingTodosByProjectId(Integer projectId) throws Exception {
        if(projectId==null){
            throw new Exception("Invalid user id");
        }
        List<Todo> todos = todoRepository.findByProjectIdAndStatus(projectId,"pending");
        return todos;
    }

    @Override
    public List<Todo> getCompletedTodosByProjectId(Integer projectId) throws Exception {
        if(projectId==null){
            throw new Exception("Invalid user id");
        }
        List<Todo> todos = todoRepository.findByProjectIdAndStatus(projectId,"completed");
        return todos;
    }

    @Override
    public List<Todo> getTodosByProjectId(Integer projectId) throws Exception {
        if(projectId==null){
            throw new Exception("Invalid user id");
        }
        List<Todo> todos = todoRepository.findByProjectId(projectId);
        return todos;
    }

    @Override
    public Todo addTodo(TodoRequest todoRequest) throws Exception {

        if(StringUtils.isEmpty(todoRequest.getDescription())){
            throw new Exception("Todo Description is empty");
        }

        Todo todo=TodoRequest.to(todoRequest);
        todo= todoRepository.save(todo);
        System.out.println(todo);
        return todo;


    }

    @Override
    public Todo getTodoById(Integer todoId) throws Exception {
        Optional<Todo> todo= todoRepository.findById(todoId);
        if (todo.isPresent()){
            return todo.get();
        }
        else {
            throw new Exception("Todo with todo id is not present");
        }

    }

    @Override
    public void deleteTodo(Integer id) {
        todoRepository.deleteById(id);
    }

    @Override
    public void markCompleted(Todo todo) {
        todo.setStatus("completed");
        todoRepository.save(todo);
    }



    @Override
    public String exportToGist(Project project, User user) throws Exception {
        List<Todo> cTodos = getCompletedTodosByProjectId(project.getId());
        List<Todo> pTodos = getPendingTodosByProjectId(project.getId());
        return publishToGist(getMdFileText(cTodos,pTodos,project),project,user.getToken());
    }

    private String publishToGist(String file, Project project, String token) throws IOException {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization","token " + token);
            Map<String, Content> map = new HashMap<>();
            map.put(project.getProjectName() + ".md",Content.builder().content(file).build());
            ExportGistRequest requestEntity = ExportGistRequest.builder()
                    .description("My Files")
                    .pub(true)
                    .files(map)
                    .build();
            String serverUrl = "https://api.github.com/gists";
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<ExportGistRequest> request = new HttpEntity<ExportGistRequest>(requestEntity,headers);
            ResponseEntity<AddToGistResponse> responseEntity;
            responseEntity = restTemplate.exchange(serverUrl, HttpMethod.POST, request, AddToGistResponse.class);
            if(HttpStatus.CREATED.equals(responseEntity.getStatusCode())){
                System.out.println(responseEntity.getStatusCode());
                return responseEntity.getBody().getUrl();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "Error while adding to gist";
    }

    private String getMdFileText(List<Todo> cTodos, List<Todo> pTodos, Project project) {
        StringBuilder sb = new StringBuilder();
        sb.append("#" + project.getProjectName()+"  \n");
        sb.append("---------------------------------------------------------------------"+"  \n");
        sb.append("---------------------------------------------------------------------"+"  \n");
        sb.append("**Summary:** " + cTodos.size() + "/" + (cTodos.size() + pTodos.size()) + " todos completed"+"  \n");
        sb.append("**Pending**" + "  \n");
        for(Todo p : pTodos){
            sb.append( "  - [ ] " + p.getDescription() + "  \n");
        }
        sb.append("  \n");
        sb.append("**Completed**" + "  \n");
        for(Todo p : cTodos){
            sb.append( "  - [x] " + p.getDescription() + "  \n");
        }
        return sb.toString();
    }
}
