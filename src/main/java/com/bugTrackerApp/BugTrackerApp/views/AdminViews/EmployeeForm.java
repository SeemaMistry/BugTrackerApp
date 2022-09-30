package com.bugTrackerApp.BugTrackerApp.views.AdminViews;

import com.bugTrackerApp.BugTrackerApp.data.entity.AccountStatus;
import com.bugTrackerApp.BugTrackerApp.data.entity.Company;
import com.bugTrackerApp.BugTrackerApp.data.entity.Employee;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

import java.util.List;

public class EmployeeForm extends FormLayout {
    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    EmailField email = new EmailField("Email");
    ComboBox<AccountStatus> accountStatus = new ComboBox<>("Account Status");
    ComboBox<Company> company = new ComboBox<>("Company");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<Employee> binder = new BeanValidationBinder<>(Employee.class);

    private Employee employee;

    public EmployeeForm(List<Company> companies, List<AccountStatus> accountStatuses) {
        addClassName("employee-form");
        // Add Bean instance field to match fields in Employee to EmployeeForm
        binder.bindInstanceFields(this);

        // populate combo boxes
        company.setItems(companies);
        company.setItemLabelGenerator(Company::getName);
        accountStatus.setItems(accountStatuses);
        accountStatus.setItemLabelGenerator(AccountStatus::getName);

        add(
                firstName,
                lastName,
                email,
                company,
                accountStatus,
                createButtonsLayout()
        );
    }

    // return horizontal layout of buttons
    private HorizontalLayout createButtonsLayout(){
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        return new HorizontalLayout(save, delete, close);

    }
    // Employee Setter
    public void setEmployee(Employee employee) {
        this.employee = employee;
        // use readBean() to bind values from Employee object to UI fields in the form
        binder.readBean(employee);
    }


}
