package com.bugTrackerApp.BugTrackerApp.views.Forms;

import com.bugTrackerApp.BugTrackerApp.data.entity.*;
import com.bugTrackerApp.BugTrackerApp.data.service.TicketSystemService;
import com.bugTrackerApp.BugTrackerApp.data.service.UserRelationsService;
import com.bugTrackerApp.BugTrackerApp.views.LoginViews.LoginView;
import com.vaadin.flow.component.*;
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
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.RouterLink;
import org.aspectj.weaver.ast.Not;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.TransactionSystemException;

import java.util.List;
import java.util.stream.Collectors;

public class RegisterForm extends FormLayout {
    // components
    TextField firstName = new TextField("First Name");
    TextField lastName = new TextField("Last Name");
    EmailField email = new EmailField("Email");
    TextField companyName = new TextField("Company Name");
    TextField username = new TextField("Username");
    TextField securityClearance = new TextField("Security Clearance");
    PasswordField password = new PasswordField("Password");
    PasswordField confirmPassword = new PasswordField("Confirm Password");

    Button save = new Button("Save");
    Button clear = new Button("Clear All Fields");
    Button close = new Button("Cancel");

    // entity
    private Employee employee = new Employee();
    private User user;
    private Company company;
    private SecurityClearance adminClearance;


    // services
    UserRelationsService URService;

    public RegisterForm(
            List<SecurityClearance> securityClearances,
            UserRelationsService URService
            ) {
        this.URService = URService;

        // get "Admin" security clearance
        adminClearance = URService.findSecurityClearanceByTitle("Admin");


        securityClearance.setValue("ADMIN");
        securityClearance.setReadOnly(true);

        add(
                firstName,
                lastName,
                email,
                companyName,
                username,
                securityClearance,
                password,
                confirmPassword,
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
        // check passwords are the same
        boolean passwordConfirmed = confirmPasswords();
        // check value uniqueness against database
        boolean companyExists = URService.companyExistByName(companyName.getValue());
        boolean usernameExists = URService.userExistsByUsername(username.getValue());
        boolean emailExists = URService.employeeExistsBy(email.getValue());

        // if password is confirmed and
        // if all values are unique then set and save Company, User, Employee
        if (!companyExists && !usernameExists && !emailExists && passwordConfirmed){
            // set and save new company
            this.company = new Company(this.companyName.getValue());
            URService.saveCompany(this.company);

            // set employee
            employee.setEmail(email.getValue());
            employee.setFirstName(firstName.getValue());
            employee.setLastName(lastName.getValue());
            employee.setSecurityClearance(this.adminClearance);
            employee.setCompany(this.company);

            // set user
            user = new User(username.getValue(), password.getValue(), Role.ADMIN);

            // set user to employee
            this.employee.setUserAccountDetail(this.user);

            // save user and employee
            URService.saveUser(this.user);
            URService.saveEmployee(this.employee);

            // display success notification
            saveSuccessful();
        }

        saveFailure(companyExists, usernameExists, emailExists, !passwordConfirmed);
    }

    private boolean confirmPasswords(){
        if (this.password.getValue().equals(this.confirmPassword.getValue())){ return true; }
        return false;
    }
    private void clearAllFields(){
        firstName.clear();
        lastName.clear();
        email.clear();
        companyName.clear();
        username.clear();
        password.clear();
        confirmPassword.clear();
    }

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

    private void saveFailure(boolean companyExists, boolean usernameExists, boolean emailExists, boolean passwordConfirmed){
        if (companyExists) {
            Notification.show("The company name you inputted already exists! Please input another name")
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
        if (usernameExists) {
            Notification.show("The username name you inputted already exists! Please input another name")
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
        if (emailExists) {
            Notification.show("The email name you inputted already exists! Please input another name")
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
        if (passwordConfirmed){
            Notification.show("Please confirm you password")
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }
}
