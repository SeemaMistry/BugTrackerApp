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
    ComboBox<SecurityClearance> securityClearance = new ComboBox<>("Security Clearance");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<Employee> binder = new BeanValidationBinder<>(Employee.class);

    private Employee employee;

    UserRelationsService URService;
    List<SecurityClearance> securityClearances;

    public EmployeeForm(
                        List<SecurityClearance> securityClearances,
                        UserRelationsService URService) {
        addClassName("employee-form");
        this.URService = URService;
        this.securityClearances = securityClearances;
        // Add Bean instance field to match fields in Employee to EmployeeForm
        binder.bindInstanceFields(this);

        // populate combo boxes
        securityClearance.setItems(securityClearances);
        securityClearance.setItemLabelGenerator(SecurityClearance::getSecurityTitle);

        add(
                firstName,
                lastName,
                email,
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

            // set the security clearance as value or null
            SecurityClearance currentSecurityClearance = this.employee.getSecurityClearance() != null ? this.employee.getSecurityClearance() : null;

            // set employee through bean binder
            binder.writeBean(this.employee);

            // set new User when User is null
            if (currentSecurityClearance == null){
                // set User using Role
                User user = Objects.equals(this.employee.getSecurityClearance().getSecurityTitle(), Role.ADMIN) ?
                        new User(this.employee.getFirstName(), this.employee.getFirstName(), Role.ADMIN) :
                        new User(this.employee.getFirstName(), this.employee.getFirstName(), Role.USER);
                // set user to employee
                this.employee.setUserAccountDetail(user);
                URService.saveUser(this.employee.getUserAccountDetail());

            } else {
                // if User exists and role has changed, update Role
                Role role = !currentSecurityClearance.equals(this.employee.getSecurityClearance()) &&
                        Objects.equals(
                                this.employee.getSecurityClearance().getSecurityTitle().toLowerCase(),
                                Role.ADMIN.getRoleName().toLowerCase()
                        ) ?
                        Role.ADMIN :
                        Role.USER;
                // update employee role
                this.employee.getUserAccountDetail().setRole(role);
                URService.saveUser(this.employee.getUserAccountDetail());
            }

            // fire save event
            fireEvent(new SaveEvent(this, employee));

        } catch (ValidationException e) {
            System.out.println("there was an error");
            e.printStackTrace();
        }
    }

    // bind form components to Employee object
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
