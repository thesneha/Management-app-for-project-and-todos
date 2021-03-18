package com.example.todolist.controller;

import com.example.todolist.request.ProjectRequest;
import com.example.todolist.service.ProjectService;
import com.example.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @Autowired
    ProjectService projectService;

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @Autowired
    UserService userService;

    @RequestMapping(value ="/", method = RequestMethod.GET)
    public ModelAndView user(@AuthenticationPrincipal OAuth2User principal, OAuth2AuthenticationToken authentication) throws Exception {
        OAuth2AuthorizedClient client=authorizedClientService
                .loadAuthorizedClient(
                        authentication.getAuthorizedClientRegistrationId(),
                        authentication.getName());

        String userId =authentication.getName();
        userService.createUser(userId,client.getAccessToken().getTokenValue());

        ProjectRequest projectRequest = ProjectRequest.builder().userId(userId).build();
        ModelAndView modelAndView = getModelAndView(projectRequest);
        return modelAndView;

    }

    @RequestMapping(value ="/", method = RequestMethod.POST)
    public ModelAndView registerUser(@ModelAttribute ProjectRequest projectRequest) throws Exception {
        String errorMsg=null;
        try{
            projectService.addProject(projectRequest);
        }
        catch (Exception e){
            e.printStackTrace();
            errorMsg=e.getMessage();

        }


        ModelAndView modelAndView = getModelAndView(projectRequest);
        modelAndView.addObject("errorMsg",errorMsg);
        return modelAndView;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    private ModelAndView deleteStudent(@RequestParam Integer id, @RequestParam String userId) throws Exception {

        ProjectRequest projectRequest = ProjectRequest.builder().userId(userId).id(id).build();
        projectService.deleteProject(projectRequest);

        ModelAndView modelAndView = getModelAndView(projectRequest);
        return modelAndView;
    }

    private ModelAndView getModelAndView(ProjectRequest projectRequest) throws Exception {
        ModelAndView modelAndView = new ModelAndView("projectPage");
        modelAndView.addObject("projectRequest", projectRequest);
        modelAndView.addObject("userName", projectRequest.getUserId());
        modelAndView.addObject("projects", projectService.getProjectByUserId(projectRequest.getUserId()));
        return modelAndView;
    }

}
