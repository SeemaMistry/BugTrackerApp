package com.bugTrackerApp.BugTrackerApp.views.Forms;

import com.bugTrackerApp.BugTrackerApp.data.entity.*;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.shared.Registration;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ProjectForm extends FormLayout {
    // Components: TextField, TextArea Multiselect ComboBox, ComboBox, Buttons
    TextField name = new TextField("Project Name");
    TextArea description = new TextArea("Project Description");
    ComboBox<Employee> creatorEmployee = new ComboBox<>("Creator");
    ComboBox<Status> projectStatus = new ComboBox<>("Status");
    MultiSelectComboBox<Employee> projectsAssignedToEmployee = new MultiSelectComboBox<>("Assigned Employees");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    // Project Object
    private Project project;

    // initialize bean binder
    Binder<Project> binder = new BeanValidationBinder<>(Project.class);

    // list to populate MultiSelectComboBox
    List<Employee> employees;

    public ProjectForm(List<Employee> employeesList, List<Status> statuses) {
        // bind instance fields
        binder.bindInstanceFields(this);

        // sort employees in alphabetical order
        employeesList.sort(Comparator.comparing(Employee::getFirstName));
        this.employees = employeesList;

        // configure comboBox and multiselect comboBox
        creatorEmployee.setItems(this.employees);
        creatorEmployee.setItemLabelGenerator(Employee::getFullName);
        projectStatus.setItems(statuses);
        projectStatus.setItemLabelGenerator(Status::getName);
        projectsAssignedToEmployee.setItems(this.employees);
        projectsAssignedToEmployee.setItemLabelGenerator(Employee::getFullName);

        add(
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

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));


        // remove save and delete buttons from view if User role is not Admin
        if (VaadinSession.getCurrent().getAttribute(User.class).getRole() != Role.ADMIN) {
            save.setVisible(false);
            delete.setVisible(false);
        }

        return new HorizontalLayout(save, delete, close);
    }

    // save valid project
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

        // deselect MultiSelectComboBox of employees
        projectsAssignedToEmployee.deselectAll();

        // populate MultiSelectComboBox with employees assigned to project
        if (project != null) {
            // for each assigned employee, add each individually as a selected subList() of the employeeList
            for(Employee eAssigned : project.getEmployeesAssignedToProject()) {
                for(Employee eFromFullList : this.employees) {
                    // check if Ids match (Employee Objects will not match even if they are the same so use .equals())
                    if(eFromFullList.getId().equals(eAssigned.getId())){
                        int eInt = this.employees.indexOf(eFromFullList);
                        projectsAssignedToEmployee.select(this.employees.subList(eInt, eInt+1 ));
                    }
                }
            }
        }
    }

    // get employees selected from MultiSelectComboBox
    public List<Employee> getEmployeesAssigned() { return new ArrayList<>(projectsAssignedToEmployee.getValue()); }

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
