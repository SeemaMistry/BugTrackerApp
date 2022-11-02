package com.bugTrackerApp.BugTrackerApp.views.AdminViews;

import com.bugTrackerApp.BugTrackerApp.data.entity.Employee;
import com.bugTrackerApp.BugTrackerApp.data.entity.Project;
import com.bugTrackerApp.BugTrackerApp.data.entity.Status;
import com.bugTrackerApp.BugTrackerApp.data.service.UserRelationsService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

import java.util.List;

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

    // initialize bean binder
    Binder<Project> binder = new BeanValidationBinder<>(Project.class);

    public ProjectForm(List<Employee> employees, List<Status> statuses) {
        H1 welcome = new H1("Edit a project's info here");
        // bind instance fields
        binder.bindInstanceFields(this);

        // configure comboBox
        creatorEmployee.setItems(employees);
        creatorEmployee.setItemLabelGenerator(Employee::getFirstName);
        projectStatus.setItems(statuses);
        projectStatus.setItemLabelGenerator(Status::getName);

        add(welcome,
                name,
                description,
                creatorEmployee,
                projectStatus,
                createButtonLayout()
        );
    }

    // return button layout
    private HorizontalLayout createButtonLayout() {
        // set button colour themes
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        return new HorizontalLayout(save, delete, close);
    }
}
