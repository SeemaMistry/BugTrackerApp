package com.bugTrackerApp.BugTrackerApp.views.Forms;

import com.bugTrackerApp.BugTrackerApp.data.entity.*;
import com.bugTrackerApp.BugTrackerApp.data.service.UserRelationsService;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.shared.Registration;
import org.springframework.boot.web.servlet.server.Session;

import java.util.List;
import java.util.Objects;

public class EmployeeForm extends FormLayout {
    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    EmailField email = new EmailField("Email");
    ComboBox<Company> company = new ComboBox<>("Company");
    ComboBox<SecurityClearance> securityClearance = new ComboBox<>("Security Clearance");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<Employee> binder = new BeanValidationBinder<>(Employee.class);

    private Employee employee;

    UserRelationsService URService;
    List<SecurityClearance> securityClearances;

    public EmployeeForm(List<Company> companies,
                        List<SecurityClearance> securityClearances,
                        UserRelationsService URService) {
        addClassName("employee-form");
        this.URService = URService;
        this.securityClearances = securityClearances;
        // Add Bean instance field to match fields in Employee to EmployeeForm
        binder.bindInstanceFields(this);

        // populate combo boxes
        company.setItems(companies);
        company.setItemLabelGenerator(Company::getName);
        securityClearance.setItems(securityClearances);
        securityClearance.setItemLabelGenerator(SecurityClearance::getSecurityTitle);

        add(
                firstName,
                lastName,
                email,
                company,
                securityClearance,
                createButtonsLayout()
        );
    }

    // return horizontal layout of buttons
    private HorizontalLayout createButtonsLayout(){
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        // fire events
        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, employee)));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        // validate form each time it changes
        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    public void validateAndSave() {
        try {
            // check if employee security clearnace has changed
//            System.out.println("\n------" + this.employee.getSecurityClearance().getSecurityTitle() + "-------\n");
            if (this.employee.getSecurityClearance() == null){
                // write bean
                binder.writeBean(this.employee);
                // set role based on security clearance
                if (Objects.equals(this.employee.getSecurityClearance().getSecurityTitle(), "Admin")){
                    User user = new User(this.employee.getFirstName(), this.employee.getFirstName(), Role.ADMIN);
                    this.employee.setUserAccountDetail(user);
                    URService.saveUser(this.employee.getUserAccountDetail());
                } else {
                    User user = new User(this.employee.getFirstName(), this.employee.getFirstName(), Role.USER);
                    this.employee.setUserAccountDetail(user);
                    URService.saveUser(this.employee.getUserAccountDetail());
                }

            } else {
                SecurityClearance currentSecurityClearance = this.employee.getSecurityClearance();
//            System.out.println("\n-------------\n");
//            System.out.println(currentSecurityClearance.getSecurityTitle());
//            System.out.println("\n-------------\n");
                binder.writeBean(employee);
                // set the user role to the security clearance
                if (!currentSecurityClearance.equals(this.employee.getSecurityClearance())) {
                    System.out.println("\n-------------\n");
                    System.out.println(this.employee.getSecurityClearance().getSecurityTitle());
                    System.out.println("\n-------------\n");
                    // change user.ROLE
                    if (Objects.equals(this.employee.getSecurityClearance().getSecurityTitle(), "Admin")) {
                        System.out.println("\n------ I Am In Admin if statement -------\n");
                        this.employee.getUserAccountDetail().setRole(Role.ADMIN);
                        URService.saveUser(this.employee.getUserAccountDetail());
                    } else {
                        System.out.println("\n------ I Am In Else if statement -------\n");
                        this.employee.getUserAccountDetail().setRole(Role.USER);
                        URService.saveUser(this.employee.getUserAccountDetail());
//                    URService.saveEmployee(this.employee);
//                    VaadinSession.getCurrent().setAttribute(User.class, this.employee.getUserAccountDetail());
                    }
                }

                fireEvent(new SaveEvent(this, employee));
            }
        } catch (ValidationException e) {
            System.out.println("there was an error");
            e.printStackTrace();
        }
    }

    // Employee Setter
    public void setEmployee(Employee employee) {
        this.employee = employee;
        // use readBean() to bind values from Employee object to UI fields in the form
        binder.readBean(employee);
    }

    // Events
    public static abstract class ContactFormEvent extends ComponentEvent<EmployeeForm> {
        private Employee employee;

        protected ContactFormEvent(EmployeeForm source, Employee employee) {
            super(source, false);
            this.employee = employee;
        }

        public Employee getEmployee() {
            return employee;
        }
    }

    public static class SaveEvent extends ContactFormEvent {
        SaveEvent(EmployeeForm source, Employee employee) {
            super(source, employee);
        }
    }

    public static class DeleteEvent extends ContactFormEvent {
        DeleteEvent(EmployeeForm source, Employee employee) {
            super(source, employee);
        }

    }

    public static class CloseEvent extends ContactFormEvent {
        CloseEvent(EmployeeForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

} // end of employeeForm
