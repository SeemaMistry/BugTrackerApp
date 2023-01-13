package com.bugTrackerApp.BugTrackerApp.views.UserViews;

import com.bugTrackerApp.BugTrackerApp.data.entity.Employee;
import com.bugTrackerApp.BugTrackerApp.data.entity.User;
import com.bugTrackerApp.BugTrackerApp.data.service.UserRelationsService;
import com.bugTrackerApp.BugTrackerApp.views.MainLayout;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import javax.annotation.security.RolesAllowed;

@PageTitle("Profile | Bug Tracker")
@Route(value="profile", layout = MainLayout.class)
@RolesAllowed({"USER", "ADMIN"})
public class ProfileView extends VerticalLayout {
    // User relation service
    UserRelationsService URService;

    // components
    TextField username = new TextField("Username");
    PasswordField password = new PasswordField("Password");
    PasswordField currentPassword = new PasswordField("Current Password");
    PasswordField newPassword = new PasswordField("New Password");
    PasswordField confirmNewPassword = new PasswordField("Confirm New Password");
    Button changePasswordBtn = new Button("Change Password");
    TextField firstName = new TextField("First Name");
    TextField lastName = new TextField("Last Name");
    EmailField email = new EmailField("Email address");
    TextField company = new TextField("Company");
    TextField securityClearance = new TextField("Job Title");
    Button save = new Button("Save");
    Button cancel  = new Button("Cancel");

    Accordion accordion = new Accordion();
    PasswordField currentPassword2 = new PasswordField("Current Password");
    PasswordField newPassword2 = new PasswordField("New Password");
    PasswordField confirmNewPassword2 = new PasswordField("Confirm New Password");

    // Employee entity
    Employee employee;

    public ProfileView(UserRelationsService URService) {
        H1 welcome = new H1("My Profile");
        this.URService = URService;

        // get current session user and set employee
        User user = VaadinSession.getCurrent().getAttribute(User.class);
        this.employee = user.getEmployee();

        // configure accordion
        createAccordion();
        // configure change password save and cancel events
        configureSaveAndCancelBtnEvents();

        add(welcome, this.accordion);
    }

    // set components in a vertical layout
    public VerticalLayout getContent() {
        VerticalLayout content = new VerticalLayout(
                this.username,
//                this.password,
                this.currentPassword,
                new VerticalLayout(this.newPassword, this.confirmNewPassword),
                new VerticalLayout(this.save, this.cancel),
                this.changePasswordBtn,
                this.firstName,
                this.lastName,
                this.email,
                this.company,
                this.securityClearance
        );
        return content;
    }

    // configure save and cancel changePassword button events
    private void configureSaveAndCancelBtnEvents(){
        this.save.addClickListener(e -> {
            // validate and save the password
            boolean passwordChangeSuccess = validateAndSave();
            // give notifications that password has changed
            Notification.show(String.format("Password change has been %s", String.valueOf(passwordChangeSuccess)));
        });

        this.cancel.addClickListener(e->{
            // close the password fields
            this.currentPassword2.setValue("");
            this.newPassword2.setValue("");
            this.confirmNewPassword2.setValue("");
        });
    }

    private boolean validateAndSave(){
        // check if currentPassword == current password
        if (this.employee.getUserAccountDetail().checkPassword(this.currentPassword2.getValue())){
            // check if newPassword == confirmNewPassword
            if (this.newPassword2.getValue().equals(this.confirmNewPassword2.getValue())){
                // change and save new password
                User user = this.employee.getUserAccountDetail();
                user.changePassword(confirmNewPassword2.getValue());
                URService.saveUser(user);
                return true;
            }
        }
        // else return false
        return false;
    }

    public void createAccordion(){
        // set personal info
        Span fullName = new Span(this.employee.getFullName());
        Span email = new Span(this.employee.getEmail());
        VerticalLayout personalInfo = new VerticalLayout(fullName, email);
        personalInfo.setSpacing(false);

        // set account info
        Span username = new Span("Username: " + this.employee.getUserAccountDetail().getUsername());
        Span password = new Span("Password: " + "********");
        VerticalLayout accountInfo = new VerticalLayout(username, password);
        accountInfo.setSpacing(false);

        // set change password info: password fields to change
        Span description = new Span("Change your password below:");
        VerticalLayout changePasswordInfo = new VerticalLayout(
                description,
                this.currentPassword2,
                this.newPassword2,
                this.confirmNewPassword2,
                new HorizontalLayout(this.save, this.cancel)
        );
        changePasswordInfo.setSpacing(false);

        // set company info
        Span companyName = new Span("Company: " + this.employee.getCompany().getName());
        Span jobTitle = new Span("Job Title: " + this.employee.getSecurityClearance().getSecurityTitle());
        VerticalLayout companyInfo = new VerticalLayout(companyName, jobTitle);
        companyInfo.setSpacing(false);

        // set accordion panels
        // Personal info: name, email
        this.accordion.add("Personal information", personalInfo);
        // Account info: username, password .. change password?
        this.accordion.add("Account information", accountInfo);
        // Account info: username, password .. change password?
        this.accordion.add("Change password", changePasswordInfo);
        // Company info:  company, job title
        this.accordion.add("Company information", companyInfo);

        this.accordion.setWidthFull();
    }
}
