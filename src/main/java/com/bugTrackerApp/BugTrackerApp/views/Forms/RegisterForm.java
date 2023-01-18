package com.bugTrackerApp.BugTrackerApp.views.Forms;

import com.bugTrackerApp.BugTrackerApp.data.entity.*;
import com.bugTrackerApp.BugTrackerApp.data.service.UserRelationsService;
import com.bugTrackerApp.BugTrackerApp.views.LoginViews.LoginView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.RouterLink;

import java.util.List;

public class RegisterForm extends FormLayout {
    // components
    TextField firstName = new TextField("firstName");
    TextField lastName = new TextField("lastName");
    TextField email = new TextField("email");
    TextField companyName = new TextField("companyName");
    TextField username = new TextField("username");
    TextField password = new TextField("password");

    Button save = new Button("Save");
    Button clear = new Button("Clear All Fields");
    Button close = new Button("Cancel");

    // entity
    private Employee employee = new Employee();
    private User user;
    private Company company;

    List<SecurityClearance> securityClearances;

    // services
    UserRelationsService URService;

    public RegisterForm(
            List<SecurityClearance> securityClearances,
            UserRelationsService URService) {
        this.URService = URService;

        this.securityClearances = securityClearances;

        add(
                firstName,
                lastName,
                email,
                companyName,
                username,
                password,
                createButtonsLayout()
        );
    }

    // return horizontal layout of buttons
    private HorizontalLayout createButtonsLayout(){
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        clear.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        close.addThemeVariants(ButtonVariant.LUMO_ERROR);

        save.addClickListener(event -> {
            try {
                validateAndSave();
            } catch (ValidationException e) {
                throw new RuntimeException(e);
            }
        });

        clear.addClickListener(e -> clearAllFields());
        close.addClickListener(e -> UI.getCurrent().navigate("login"));

        return new HorizontalLayout(save, clear, close);
    }

    private void validateAndSave() throws ValidationException {
        // set and save new company
        this.company = new Company(this.companyName.getValue());
        URService.saveCompany(this.company);

        // set employee
        employee.setEmail(email.getValue());
        employee.setFirstName(firstName.getValue());
        employee.setLastName(lastName.getValue());
        employee.setSecurityClearance(this.securityClearances.get(1));
        employee.setCompany(this.company);

        // set user
        user = new User(username.getValue(), password.getValue(), Role.ADMIN);

        // set user to employee
        this.employee.setUserAccountDetail(this.user);

        // save user and employee
        URService.saveUser(this.user);
        URService.saveEmployee(this.employee);

        saveSuccessful();
    }

    private void clearAllFields(){
        firstName.clear();
        lastName.clear();
        email.clear();
        companyName.clear();
        username.clear();
        password.clear();
    }

    // bind form components to Employee, Company and User objects
    public void setNewAccount(){}



    // Events
    public void saveSuccessful(){
        Notification notification = new Notification();

        // set close button
        Button closeButton = new Button(new Icon("lumo", "cross"));
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        closeButton.getElement().setAttribute("aria-label", "Close");
        closeButton.addClickListener(event -> {
            notification.close();
            UI.getCurrent().navigate("login");
        });

        // set router link to login page
        RouterLink login = new RouterLink("Click here to login with your new account.  ", LoginView.class);

        notification.add(
                new Text("Successful Account Creation! "),
                login,
                closeButton
        );

        notification.setPosition(Notification.Position.MIDDLE);
        notification.open();
    }
}
