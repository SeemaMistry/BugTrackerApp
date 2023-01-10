package com.bugTrackerApp.BugTrackerApp.views.LoginViews;

import com.bugTrackerApp.BugTrackerApp.security.AuthService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
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

    // components
    Button userDemoBtn = new Button();
    Button adminDemoBtn = new Button();

    public LoginView(AuthService authService) {
        this.authService = authService;
        addClassName("login-view");
        TextField username = new TextField("Username");
        PasswordField password = new PasswordField("Password");

        // configure User and Admin Demo account Buttons
        configureDemoBtns();

        // place login form in a vertical layout
        VerticalLayout loginForm = new VerticalLayout(new H1("Ticket System Login"),
                username,
                password,
                new Button("Login", e -> {
                    try {
                        // verify login credentials
                        authService.authenticate(username.getValue(), password.getValue());
                        // navigate to HomeView.class
                        UI.getCurrent().navigate("");
                    } catch (AuthService.AuthException ex) {
                        Notification.show("Wrong credentials");
                    }
                }),
                new HorizontalLayout(this.userDemoBtn, this.adminDemoBtn)
        );

        // centre login form
        loginForm.setJustifyContentMode(JustifyContentMode.CENTER);
        loginForm.setAlignItems(Alignment.CENTER);

        add(loginForm);
    }

    // configure buttons to load User or Admin demo accounts
    private void configureDemoBtns(){
        // set button icons and text
        Icon userIcon = new Icon(VaadinIcon.USER);
        Icon adminIcon = new Icon(VaadinIcon.USER_STAR);
        this.userDemoBtn.setIcon(userIcon);
        this.adminDemoBtn.setIcon(adminIcon);
        this.userDemoBtn.setText("User Demo Account");
        this.adminDemoBtn.setText("Admin Demo Account!");

        // make buttons large size
        this.userDemoBtn.addThemeVariants(ButtonVariant.LUMO_LARGE);
        this.adminDemoBtn.addThemeVariants(ButtonVariant.LUMO_LARGE);

        // set click event to call authservice.authenticate with admin/user values
        this.userDemoBtn.addClickListener(e -> {
            try {
                // verify login credentials
                authService.authenticate("Barry", "Barry");
                // navigate to HomeView.class
                UI.getCurrent().navigate("");
            } catch (AuthService.AuthException ex) {
                Notification.show("Wrong credentials");
            }
        });

        this.adminDemoBtn.addClickListener(e -> {
            try {
                // verify login credentials
                authService.authenticate("admin", "admin");
                // navigate to HomeView.class
                UI.getCurrent().navigate("");
            } catch (AuthService.AuthException ex) {
                Notification.show("Wrong credentials");
            }
        });


    }
}
