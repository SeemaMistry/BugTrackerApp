package com.bugTrackerApp.BugTrackerApp.data.service;

import com.bugTrackerApp.BugTrackerApp.data.entity.User;
import com.bugTrackerApp.BugTrackerApp.data.repository.UserRepository;
import com.vaadin.flow.server.VaadinSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AuthService {
    UserRepository userRepository;


    // retrieve User from the database if they exist and set Session and Routes
    public void authenticate(String username, String password){
        // retrieve user from database
        User user = userRepository.getByUsername(username);
        // if user exists, set session and create routes
        if (user != null && user.checkPassword(password)){
            VaadinSession.getCurrent().setAttribute(User.class, user);
            createRoutes(user.getRole());
        }
    }

}
