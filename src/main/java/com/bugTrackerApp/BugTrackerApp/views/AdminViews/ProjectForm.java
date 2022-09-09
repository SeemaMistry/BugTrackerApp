package com.bugTrackerApp.BugTrackerApp.views.AdminViews;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;

public class ProjectForm extends FormLayout {
    public ProjectForm() {
        H1 welcome = new H1("Edit a project's info here");
        add(welcome);
    }
}
