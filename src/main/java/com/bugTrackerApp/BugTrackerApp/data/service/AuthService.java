package com.bugTrackerApp.BugTrackerApp.data.service;

import com.bugTrackerApp.BugTrackerApp.data.entity.Role;
import com.bugTrackerApp.BugTrackerApp.data.entity.User;
import com.bugTrackerApp.BugTrackerApp.data.repository.UserRepository;
import com.bugTrackerApp.BugTrackerApp.views.AdminViews.EmployeesList;
import com.bugTrackerApp.BugTrackerApp.views.HomeView;
import com.bugTrackerApp.BugTrackerApp.views.LogoutViews.LogOut;
import com.bugTrackerApp.BugTrackerApp.views.MainLayout;
import com.bugTrackerApp.BugTrackerApp.views.UserViews.ProfileView;
import com.bugTrackerApp.BugTrackerApp.views.UserViews.ProjectsList;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.VaadinSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class AuthService {
    UserRepository userRepository;

    // Exception Class
    public class AuthException extends Exception{}


    // retrieve User from the database if they exist and set Session and Routes
    public void authenticate(String username, String password) throws AuthException {
        // retrieve user from database
        User user = userRepository.getByUsername(username);
        // if user exists, set session and create routes
        if (user != null && user.checkPassword(password)){
            VaadinSession.getCurrent().setAttribute(User.class, user);
            createRoutes(user.getRole());
        } else {
            throw new AuthException();
        }
    }

    // create Routes based on role
    private void createRoutes(Role role){
        // get the role and stream the routes authorized to this role
        getAuthorizedRoutes(role).stream()
                .forEach(route -> RouteConfiguration.forSessionScope()
                        .setRoute(route.path, route.view, MainLayout.class));
    }

    public List<AuthorizedRoute> getAuthorizedRoutes(Role role) {
        // create a list of AuthorizedRoutes based on User Role
        List<AuthorizedRoute> routes = new ArrayList<>();

        // base case: User
        routes.add(new AuthorizedRoute("", "Home", HomeView.class));
        routes.add(new AuthorizedRoute("projects", "Projects", ProjectsList.class));
        routes.add(new AuthorizedRoute("profile", "My Profile", ProfileView.class));

        // advanced case: Admin
        if (role.equals(Role.ADMIN)) {
            routes.add(new AuthorizedRoute("employees", "Employees", EmployeesList.class));
        }

        // add logout button
        routes.add(new AuthorizedRoute("logout", "Logout", LogOut.class));

        return routes;
    }

}
