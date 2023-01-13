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
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
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
    Button save = new Button("Save");
    Button cancel  = new Button("Cancel");

    Accordion accordion = new Accordion();

    PasswordField currentPassword = new PasswordField("Current Password");
    PasswordField newPassword = new PasswordField("New Password");
    PasswordField confirmNewPassword = new PasswordField("Confirm New Password");

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

    // configure save and cancel changePassword button events
    private void configureSaveAndCancelBtnEvents(){
        this.save.addClickListener(e -> {
            // validate and save the password
            boolean passwordChangeSuccess = validateAndSave();
            // give notifications that password has changed
            if (passwordChangeSuccess) {
                Notification.show("Password change has been successful!")
                        .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            } else {
                Notification.show("Password change failed!")
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
            // clear all the values
            this.currentPassword.setValue("");
            this.newPassword.setValue("");
            this.confirmNewPassword.setValue("");
        });

        this.cancel.addClickListener(e->{
            // close the password fields
            this.currentPassword.setValue("");
            this.newPassword.setValue("");
            this.confirmNewPassword.setValue("");
        });
    }

    private boolean validateAndSave(){
        // check if currentPassword == current password
        if (this.employee.getUserAccountDetail().checkPassword(this.currentPassword.getValue())){
            // check if newPassword == confirmNewPassword
            if (this.newPassword.getValue().equals(this.confirmNewPassword.getValue())){
                // change and save new password
                User user = this.employee.getUserAccountDetail();
                user.changePassword(confirmNewPassword.getValue());
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
                this.currentPassword,
                this.newPassword,
                this.confirmNewPassword,
                new HorizontalLayout(this.save, this.cancel)
        );
        changePasswordInfo.setSpacing(false);

        // set company info
        Span companyName = new Span("Company: " + this.employee.getCompany().getName());
        Span jobTitle = new Span("Job Title: " + this.employee.getSecurityClearance().getSecurityTitle());
        VerticalLayout companyInfo = new VerticalLayout(companyName, jobTitle);
        companyInfo.setSpacing(false);

        // set accordion panels
        this.accordion.add("Personal information", personalInfo);
        this.accordion.add("Account information", accountInfo);
        this.accordion.add("Change password", changePasswordInfo);
        this.accordion.add("Company information", companyInfo);

        this.accordion.setWidthFull();
    }
}
