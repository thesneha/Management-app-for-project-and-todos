package com.example.todolist.controller;

import com.example.todolist.model.Project;
import com.example.todolist.model.Todo;
import com.example.todolist.model.User;
import com.example.todolist.repository.ProjectRepository;
import com.example.todolist.request.TodoRequest;
import com.example.todolist.service.ProjectService;
import com.example.todolist.service.TodoService;
import com.example.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class TodoController {

    @Autowired
    TodoService todoService;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ProjectService projectService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/todo", method = RequestMethod.GET)
    private ModelAndView viewProject(@RequestParam Integer projectId, @RequestParam String userId) throws Exception {
        if (userId==null)
        {
            return new ModelAndView(new RedirectView("projectPage"));
        }

        Project project= projectService.getByProjectId(projectId);
        TodoRequest todoRequest= TodoRequest.builder().userId(userId).projectId(projectId).build();

        ModelAndView modelAndView = new ModelAndView("todoPage");
        modelAndView.addObject("project",project );
        modelAndView.addObject("gistURL", null);
        modelAndView.addObject("todoRequest", todoRequest);
        modelAndView.addObject("userId", todoRequest.getUserId());
        modelAndView.addObject("todos", todoService.getPendingTodosByProjectId(projectId));
        modelAndView.addObject("completedTodos", todoService.getCompletedTodosByProjectId(projectId));
//        modelAndView.addObject("todoRequest", TodoRequest.builder().build());
        return modelAndView;
    }


    @RequestMapping(value ="/todo", method = RequestMethod.POST)
    public ModelAndView registerUser(@ModelAttribute TodoRequest todoRequest) throws Exception {

        String errorMsg=null;
        try{
            todoService.addTodo(todoRequest);
        }
        catch (Exception e){
            e.printStackTrace();
            errorMsg=e.getMessage();

        }

        Project project= projectService.getByProjectId(todoRequest.getProjectId());
        project = projectService.incrementTodos(project);
        ModelAndView modelAndView = new ModelAndView("todoPage");
        modelAndView.addObject("project",project );
        modelAndView.addObject("errorMsg",errorMsg);
        modelAndView.addObject("gistURL", null);
        modelAndView.addObject("todoRequest", todoRequest);
        modelAndView.addObject("userId", todoRequest.getUserId());
        modelAndView.addObject("todos", todoService.getPendingTodosByProjectId(project.getId()));
        modelAndView.addObject("completedTodos", todoService.getCompletedTodosByProjectId(project.getId()));
        return modelAndView;

    }

    @RequestMapping(value = "/deleteTodo", method = RequestMethod.GET)
    private ModelAndView deleteStudent(@RequestParam Integer id, @RequestParam String userId) throws Exception {
        if (id==null)
        {
            return new ModelAndView(new RedirectView("projectPage"));
        }
        Todo todo = todoService.getTodoById(id);
        todoService.deleteTodo(id);
        Project project= projectService.getByProjectId(todo.getProjectId());
        TodoRequest todoRequest= TodoRequest.builder().userId(userId).projectId(project.getId()).build();

        ModelAndView modelAndView = new ModelAndView("todoPage");
        modelAndView.addObject("project",project );
        modelAndView.addObject("gistURL", null);
        modelAndView.addObject("todoRequest", todoRequest);
        modelAndView.addObject("userId", todoRequest.getUserId());
        modelAndView.addObject("todos", todoService.getPendingTodosByProjectId(project.getId()));
        modelAndView.addObject("completedTodos", todoService.getCompletedTodosByProjectId(project.getId()));
        return modelAndView;
    }

    @RequestMapping(value = "/complete", method = RequestMethod.GET)
    private ModelAndView complete(@RequestParam Integer id, @RequestParam String userId) throws Exception {
        Todo todo = todoService.getTodoById(id);
        todoService.markCompleted(todo);
        Project project= projectService.getByProjectId(todo.getProjectId());
        project = projectService.incrementCompleted(project);
        TodoRequest todoRequest= TodoRequest.builder().userId(userId).projectId(project.getId()).build();

        ModelAndView modelAndView = new ModelAndView("todoPage");
        modelAndView.addObject("project",project );
        modelAndView.addObject("gistURL", null);
        modelAndView.addObject("todoRequest", todoRequest);
        modelAndView.addObject("userId", todoRequest.getUserId());
        modelAndView.addObject("todos", todoService.getPendingTodosByProjectId(project.getId()));
        modelAndView.addObject("completedTodos", todoService.getCompletedTodosByProjectId(project.getId()));
        return modelAndView;
    }

    @RequestMapping(value = "/exportAsGist", method = RequestMethod.GET)
    private ModelAndView complete(@RequestParam Integer id) throws Exception {
        Project project= projectService.getByProjectId(id);
        User user= userService.getUserById(project.getUserId());
        String gistUrl = todoService.exportToGist(project,user);
        TodoRequest todoRequest= TodoRequest.builder().userId(user.getUserId()).projectId(project.getId()).build();
        ModelAndView modelAndView = new ModelAndView("todoPage");
        modelAndView.addObject("gistURL", gistUrl);
        modelAndView.addObject("project",project );
        modelAndView.addObject("todoRequest", todoRequest);
        modelAndView.addObject("userId", todoRequest.getUserId());
        modelAndView.addObject("todos", todoService.getPendingTodosByProjectId(project.getId()));
        modelAndView.addObject("completedTodos", todoService.getCompletedTodosByProjectId(project.getId()));
        return modelAndView;
    }

}
