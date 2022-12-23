package com.bugTrackerApp.BugTrackerApp.views.UserViews;

import com.bugTrackerApp.BugTrackerApp.data.entity.Employee;
import com.bugTrackerApp.BugTrackerApp.data.service.UserRelationsService;
import com.bugTrackerApp.BugTrackerApp.views.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

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
    TextField firstName = new TextField("First Name");
    TextField lastName = new TextField("Last Name");
    EmailField email = new EmailField("Email address");
    TextField company = new TextField("Company");
    TextField securityClearance = new TextField("Job Title");

    // Employee entity
    Employee employee;

    // binder
    Binder<Employee> binder = new BeanValidationBinder<>(Employee.class);

    public ProfileView(UserRelationsService URService) {
        H1 welcome = new H1("My Profile");
        this.URService = URService;
        // hardcode in an employee
        List<Employee> employeeList = URService.findAllEmployees(null);
        this.employee = employeeList.get(0);

        // configure my profile components
        configureComponents();

        add(welcome, getContent());
    }

    public void configureComponents() {
        this.firstName.setValue(this.employee.getFirstName());
        this.lastName.setValue(this.employee.getLastName());
        this.email.setValue(this.employee.getEmail());
        this.company.setValue("co company");
        this.securityClearance.setValue(this.employee.getSecurityClearance().getSecurityTitle());

        // set size to 300px
        this.firstName.setWidth("300px");
        this.lastName.setWidth("300px");
        this.email.setWidth("300px");
        this.company.setWidth("300px");
        this.securityClearance.setWidth("300px");

        // make all read only
        this.firstName.setReadOnly(true);
        this.lastName.setReadOnly(true);
        this.email.setReadOnly(true);
        this.company.setReadOnly(true);
        this.securityClearance.setReadOnly(true);
    }

    public VerticalLayout getContent() {
        VerticalLayout content = new VerticalLayout(
                this.firstName,
                this.lastName,
                this.email,
                this.company,
                this.securityClearance
        );
        return content;
    }
}
