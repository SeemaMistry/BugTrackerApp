package com.bugTrackerApp.BugTrackerApp.views.AdminViews;

import com.bugTrackerApp.BugTrackerApp.data.entity.Employee;
import com.bugTrackerApp.BugTrackerApp.data.entity.Project;
import com.bugTrackerApp.BugTrackerApp.data.entity.Status;
import com.bugTrackerApp.BugTrackerApp.data.service.UserRelationsService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

public class ProjectForm extends FormLayout {
    // fields and components
    TextField name = new TextField("Project Name");
    TextArea description = new TextArea("Project Description"); // might need to set label
    ComboBox<Employee> creatorEmployee = new ComboBox<Employee>("Creator");
    ComboBox<Status> projectStatus = new ComboBox<Status>("Status");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    private Project project;

    public ProjectForm() {
        H1 welcome = new H1("Edit a project's info here");
        add(welcome);
    }
}
