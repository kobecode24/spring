package com.technova.usermanagement.controller;

import com.technova.usermanagement.model.User;
import com.technova.usermanagement.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import java.util.Optional;

@Setter
public class UserController implements Controller {

    private UserService userService;

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String path = request.getRequestURI();
        if (path.endsWith("/users")) {
            return listUsers();
        } else if (path.endsWith("/users/new")) {
            return newUserForm();
        } else if (path.matches("/users/\\d+/edit")) {
            Long id = extractIdFromPath(path);
            return editUserForm(id);
        } else if (path.matches("/users/\\d+/delete")) {
            Long id = extractIdFromPath(path);
            return deleteUser(id);
        } else if (path.endsWith("/users/save")){
            User user = new User();
            user.setUsername(request.getParameter("username"));
            user.setEmail(request.getParameter("email"));
            user.setPassword(request.getParameter("password"));
            return saveUser(user);
        }

        return new ModelAndView("redirect:/users");
    }

    private ModelAndView listUsers() {
        ModelAndView modelAndView = new ModelAndView("userList");
        modelAndView.addObject("users", userService.getAllUsers());
        return modelAndView;
    }

    private ModelAndView newUserForm() {
        ModelAndView modelAndView = new ModelAndView("userForm");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    private ModelAndView saveUser(User user) {
        userService.saveUser(user);
        return new ModelAndView("redirect:/users");
    }

    private ModelAndView editUserForm(Long id) {
        ModelAndView modelAndView = new ModelAndView("userForm");
        Optional<User> user = userService.getUserById(id);
        user.ifPresent(value -> modelAndView.addObject("user", value));
        return modelAndView;
    }

    private ModelAndView deleteUser(Long id) {
        userService.deleteUser(id);
        return new ModelAndView("redirect:/users");
    }

    private Long extractIdFromPath(String path) {
        String[] segments = path.split("/");
        return Long.parseLong(segments[segments.length - 2]);
    }
}

