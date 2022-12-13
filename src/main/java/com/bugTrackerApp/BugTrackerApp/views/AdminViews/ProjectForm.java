package com.bugTrackerApp.BugTrackerApp.views.AdminViews;

import com.bugTrackerApp.BugTrackerApp.data.entity.Company;
import com.bugTrackerApp.BugTrackerApp.data.entity.Employee;
import com.bugTrackerApp.BugTrackerApp.data.entity.Project;
import com.bugTrackerApp.BugTrackerApp.data.entity.Status;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class ProjectForm extends FormLayout {
    // fields and components
    TextField name = new TextField("Project Name");
    TextArea description = new TextArea("Project Description");
    ComboBox<Employee> creatorEmployee = new ComboBox<>("Creator");
    ComboBox<Status> projectStatus = new ComboBox<>("Status");
    MultiSelectComboBox<Employee> projectsAssignedToEmployee = new MultiSelectComboBox<>("Assigned Employees");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    // Project and Company object
    private Project project;
    private Company company;

    // initialize bean binder
    Binder<Project> binder = new BeanValidationBinder<>(Project.class);

    public ProjectForm(List<Employee> employees, List<Status> statuses) {
        H1 welcome = new H1("Edit a project's info here");
        // bind instance fields
        binder.bindInstanceFields(this);

        // configure comboBox and multiselect comboBox
        creatorEmployee.setItems(employees);
        creatorEmployee.setItemLabelGenerator(Employee::getFullName);
        projectStatus.setItems(statuses);
        projectStatus.setItemLabelGenerator(Status::getName);
        projectsAssignedToEmployee.setItems(employees);
        projectsAssignedToEmployee.setItemLabelGenerator(Employee::getFullName);

        add(welcome,
                name,
                description,
                creatorEmployee,
                projectStatus,
                projectsAssignedToEmployee,
                createButtonLayout()
        );
    }

    // return button layout
    private HorizontalLayout createButtonLayout() {
        // set button colour themes
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        // fire events
        save.addClickListener(e -> validateAndSave());
        delete.addClickListener(e -> fireEvent(new ProjectForm.DeleteEvent(this, project)));
        close.addClickListener(e -> fireEvent(new ProjectForm.CloseEvent(this)));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(project);
            fireEvent(new ProjectForm.SaveEvent(this, project));
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }
    }

    // Project Setter
    public void setProject(Project project) {
        this.project = project;
        // use readBean() to bind values from project object to UI fields in the form
        binder.readBean(project);
    }

    // Events
    public static abstract class ContactFormEvent extends ComponentEvent<ProjectForm> {
        private Project project;

        protected ContactFormEvent(ProjectForm source, Project project) {
            super(source, false);
            this.project = project;
        }

        public Project getProject() {
            return project;
        }
    }

 
    public static class SaveEvent extends ProjectForm.ContactFormEvent {
        SaveEvent(ProjectForm source, Project project) {
            super(source, project);
        }
    }

    public static class DeleteEvent extends ProjectForm.ContactFormEvent {
        DeleteEvent(ProjectForm source, Project project) {
            super(source, project);
        }

    }

    public static class CloseEvent extends ProjectForm.ContactFormEvent {
        CloseEvent(ProjectForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
