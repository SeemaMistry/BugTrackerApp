package com.bugTrackerApp.BugTrackerApp.views.LoginViews;

import com.bugTrackerApp.BugTrackerApp.data.service.AuthService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;

import javax.annotation.security.PermitAll;

@PageTitle("Login | Bug Tracker")
@Route(value="login")
@RouteAlias("")
@PermitAll
public class LoginView extends VerticalLayout {
    AuthService authService;
    public LoginView(AuthService authService) {
        this.authService = authService;
        addClassName("login-view");
        TextField username = new TextField("Username");
        PasswordField password = new PasswordField("Password");

        // place login form in a vertical layout
        VerticalLayout loginForm = new VerticalLayout(new H1("Ticket System Login"),
                username,
                password,
                new Button("Login", e -> {
                    try {
                        // verify login credentials
                        authService.authenticate(username.getValue(), password.getValue());
                        // nagivate to HomeView.class
                        UI.getCurrent().navigate("");
                    } catch (AuthService.AuthException ex) {
                        Notification.show("Wrong credentials");
                    }

                })
        );

        // centre login form
        loginForm.setJustifyContentMode(JustifyContentMode.CENTER);
        loginForm.setAlignItems(Alignment.CENTER);

        add(loginForm);
    }
}
