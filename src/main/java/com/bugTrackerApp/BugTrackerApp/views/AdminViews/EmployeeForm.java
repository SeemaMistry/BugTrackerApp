package com.bugTrackerApp.BugTrackerApp.views.AdminViews;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;

public class EmployeeForm extends FormLayout {
    public EmployeeForm() {
        H1 welcome = new H1("Edit an employee's info here");
        add(welcome);
    }
}
