package com.bugTrackerApp.BugTrackerApp.views.Forms;

import com.bugTrackerApp.BugTrackerApp.data.entity.Employee;
import com.bugTrackerApp.BugTrackerApp.data.service.UserRelationsService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

public class RegisterForm extends FormLayout {
    // components
    TextField firstName = new TextField();
    TextField lastName = new TextField();
    TextField email = new TextField();
    TextField companyName = new TextField();
    TextField username = new TextField();
    TextField password = new TextField();

    Button save = new Button("Save");
    Button delete = new Button("Clear");
    Button close = new Button("Cancel");

    // binder
    Binder<Employee> binder = new BeanValidationBinder<>(Employee.class);

    // entity
    private Employee employee;


    // services
    UserRelationsService URService;

    public RegisterForm( UserRelationsService URService) {
        this.URService = URService;
        binder.bindInstanceFields(this);

        add();
    }

    // return horizontal layout of buttons
    private HorizontalLayout createButtonsLayout(){
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        // validate form each time it changes
        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave(){}

    // bind form components to Employee, Company and User objects
    public void setNewAccount(){}


    // Events

}
