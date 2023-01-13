package com.bugTrackerApp.BugTrackerApp.views.UserViews;

import com.bugTrackerApp.BugTrackerApp.data.entity.Employee;
import com.bugTrackerApp.BugTrackerApp.data.entity.User;
import com.bugTrackerApp.BugTrackerApp.data.service.UserRelationsService;
import com.bugTrackerApp.BugTrackerApp.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.util.List;

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

    // Employee entity
    Employee employee;

    public ProfileView(UserRelationsService URService) {
        H1 welcome = new H1("My Profile");
        this.URService = URService;

        // get current session user and set employee
        User user = VaadinSession.getCurrent().getAttribute(User.class);
        this.employee = user.getEmployee();

        // configure my profile components
        configureComponents();
        configureChangePasswordBtns();

        add(welcome, getContent());
    }

    // configure component values and width to 300px
    public void configureComponents() {
        this.username.setValue(this.employee.getUserAccountDetail().getUsername());
        this.password.setValue("********");
        this.firstName.setValue(this.employee.getFirstName());
        this.lastName.setValue(this.employee.getLastName());
        this.email.setValue(this.employee.getEmail());
        this.company.setValue(this.employee.getCompany().getName());
        this.securityClearance.setValue(this.employee.getSecurityClearance().getSecurityTitle());

        // set size to 300px
        this.firstName.setWidth("300px");
        this.lastName.setWidth("300px");
        this.email.setWidth("300px");
        this.company.setWidth("300px");
        this.securityClearance.setWidth("300px");

        // make all read only
        this.username.setReadOnly(true);
        this.firstName.setReadOnly(true);
        this.lastName.setReadOnly(true);
        this.email.setReadOnly(true);
        this.company.setReadOnly(true);
        this.securityClearance.setReadOnly(true);

        // make change password fields not visible
        this.currentPassword.setVisible(false);
        this.newPassword.setVisible(false);
        this.confirmNewPassword.setVisible(false);
        this.save.setVisible(false);
        this.cancel.setVisible(false);
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

    private void configureChangePasswordBtns(){
        this.changePasswordBtn.addClickListener(e -> {
            // show password fields for changing password
            this.currentPassword.setVisible(true);
            this.newPassword.setVisible(true);
            this.confirmNewPassword.setVisible(true);
            this.save.setVisible(true);
            this.cancel.setVisible(false);
        });

        this.save.addClickListener(e -> {
            // validate and save the password
            boolean passwordChangeSuccess = validateAndSave();
            // give notifications that password has changed
            Notification.show(String.format("Password change has been %s", String.valueOf(passwordChangeSuccess)));
        });

        this.cancel.addClickListener(e->{
            // close the password fields
            this.currentPassword.setVisible(false);
            this.newPassword.setVisible(false);
            this.confirmNewPassword.setVisible(false);
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
}
