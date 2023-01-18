package com.bugTrackerApp.BugTrackerApp.views.LoginViews;

import com.bugTrackerApp.BugTrackerApp.data.repository.UserRepository;
import com.bugTrackerApp.BugTrackerApp.data.service.UserRelationsService;
import com.bugTrackerApp.BugTrackerApp.views.Forms.RegisterForm;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;

@PageTitle("Register | Bug Tracker")
@Route(value="register")
@PermitAll
public class RegisterView extends VerticalLayout {
    // services
    UserRelationsService URService;

    // components
    RegisterForm registerForm;

    public RegisterView() {
        H1 welcome = new H1("Register a New Account");
        Span instructions = new Span("Create a new ADMIN account for a new company below.\n" +
                "If you are trying to create an account under an EXISTING company, please contact your company " +
                "administers to create register your new user account");

        // configure form
        configureForm();
        
        add(welcome, instructions, registerForm);
    }

    /* ------------------- CONFIGURATIONS -------------------
     * */

    // configure registerForm
    private void configureForm(){
        registerForm = new RegisterForm();
    }

    /* ------------------- GET COMPONENTS -------------------
     * */


    /* ------------------- FORM MANIPULATIONS -------------------
     * add new account, save, cancel, clear
     * */

    // save new company and employee
    private void saveAccount(){}

    // cancel redirects to welcome page
    private void cancel(){}

    // clear values in form
    private void clearForm(){}

}
