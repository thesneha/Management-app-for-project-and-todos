package com.example.todolist.controller;

import com.example.todolist.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "")
public class ProjectController {

    @Autowired
    ProjectService projectService;


//    @RequestMapping(value ="/project", method = RequestMethod.POST)
//    public ModelAndView registerUser(@ModelAttribute ProjectRequest addProjectRequest)
//    {
//        System.out.println(projectService.addProject(addProjectRequest));
//        ModelAndView modelAndView = new ModelAndView("index");
//        modelAndView.addObject("addProjectRequest", addProjectRequest);
//        return modelAndView;
//    }

//    @RequestMapping(value ="/getAll/{id}", method = RequestMethod.GET)
//    public List<Project> getAllProjectsBuUserId(@PathVariable Integer id)
//    {
//        return null;
//    }

}
