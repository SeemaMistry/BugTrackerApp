package com.bugTrackerApp.BugTrackerApp.views.LoginViews;

import com.bugTrackerApp.BugTrackerApp.data.entity.User;
import com.bugTrackerApp.BugTrackerApp.security.AuthService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextAreaVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.AmbiguousRouteConfigurationException;
import com.vaadin.flow.server.VaadinSession;

import javax.annotation.security.PermitAll;

@PageTitle("Login | Bug Tracker")
@Route(value="login")
@RouteAlias("")
@PermitAll
public class LoginView extends VerticalLayout {
    AuthService authService;

    // components
    Button userDemoBtn = new Button();
    Button adminDemoBtn = new Button();
    Button userDemoBtn2 = new Button();
    Button adminDemoBtn2 = new Button();

    Accordion noAccOptions = new Accordion();

    public LoginView(AuthService authService) {
        this.authService = authService;

        // if user session already in session, then redirect to HomeView.class
        if (VaadinSession.getCurrent().getAttribute(User.class) != null){
            UI.getCurrent().getPage().setLocation("home");
        }


        addClassName("login-view");
        TextField username = new TextField("Username");
        PasswordField password = new PasswordField("Password");

        // configure User and Admin Demo account Buttons
        configureDemoBtns();
        configureAccordion();

        // place login form in a vertical layout
        VerticalLayout loginForm = new VerticalLayout(new H1("Ticket System Login"),
                username,
                password,
                new Button("Login", e -> {
                    try {
                        // verify login credentials
                        authService.authenticate(username.getValue(), password.getValue());
                        // navigate to HomeView.class
                        UI.getCurrent().navigate("");

                    } catch (AuthService.AuthException ex) {
                        Notification.show("Wrong credentials");
                    } catch (AmbiguousRouteConfigurationException exception){
                        // clear any session that you were on
                        VaadinSession.getCurrent().getSession().invalidate();
                        VaadinSession.getCurrent().close();

                    }
                }),
                new H3("Don't have an Account? Are you looking to ..."),
                this.noAccOptions

        );

        // centre login form
        loginForm.setJustifyContentMode(JustifyContentMode.CENTER);
        loginForm.setAlignItems(Alignment.CENTER);

        add(loginForm);
    }

    /* ------------------- CONFIGURATIONS -------------------
     * */

    // configure buttons to load User or Admin demo accounts
    private void configureDemoBtns(){
        // set button icons and text
        Icon userIcon = new Icon(VaadinIcon.USER);
        Icon adminIcon = new Icon(VaadinIcon.USER_STAR);
        Icon userIcon2 = new Icon(VaadinIcon.USER);
        Icon adminIcon2 = new Icon(VaadinIcon.USER_STAR);
        this.userDemoBtn.setIcon(userIcon);
        this.adminDemoBtn.setIcon(adminIcon);
        this.userDemoBtn.setText("User Demo Account!");
        this.adminDemoBtn.setText("Admin Demo Account!");

        this.userDemoBtn2.setIcon(userIcon2);
        this.adminDemoBtn2.setIcon(adminIcon2);
        this.userDemoBtn2.setText("User Demo Account 2!");
        this.adminDemoBtn2.setText("Admin Demo Account 2!");

        // make buttons large size
        this.userDemoBtn.addThemeVariants(ButtonVariant.LUMO_LARGE);
        this.adminDemoBtn.addThemeVariants(ButtonVariant.LUMO_LARGE);

        this.adminDemoBtn2.addThemeVariants(ButtonVariant.LUMO_ERROR);
        this.userDemoBtn2.addThemeVariants(ButtonVariant.LUMO_ERROR);

        // set click event to call authService.authenticate with admin/user values
        this.userDemoBtn.addClickListener(e -> {
            try {
                // verify login credentials
                authService.authenticate("Barry", "Barry");
                // navigate to HomeView.class
                UI.getCurrent().navigate("");
            } catch (AuthService.AuthException ex) {
                Notification.show("Wrong credentials");
            } catch (AmbiguousRouteConfigurationException exception){
                // clear any session that you were on
                VaadinSession.getCurrent().getSession().invalidate();
                VaadinSession.getCurrent().close();

            }
        });

        this.adminDemoBtn.addClickListener(e -> {
            try {
                // verify login credentials
                authService.authenticate("admin", "admin");
                // navigate to HomeView.class
                UI.getCurrent().navigate("");
            } catch (AuthService.AuthException ex) {
                Notification.show("Wrong credentials");
            } catch (AmbiguousRouteConfigurationException exception){
                // clear any session that you were on
                VaadinSession.getCurrent().getSession().invalidate();
                VaadinSession.getCurrent().close();

            }
        });

        this.adminDemoBtn2.addClickListener(e -> {
            try {
                // verify login credentials
                authService.authenticate("admin2", "admin2");
                // navigate to HomeView.class
                UI.getCurrent().navigate("");
            } catch (AuthService.AuthException ex) {
                Notification.show("Wrong credentials");
            } catch (AmbiguousRouteConfigurationException exception){
                // clear any session that you were on
                VaadinSession.getCurrent().getSession().invalidate();
                VaadinSession.getCurrent().close();

            }
        });

        this.userDemoBtn2.addClickListener(e -> {
            try {
                // verify login credentials
                authService.authenticate("Mabel", "Mabel");
                // navigate to HomeView.class
                UI.getCurrent().navigate("");
            } catch (AuthService.AuthException ex) {
                Notification.show("Wrong credentials");
            } catch (AmbiguousRouteConfigurationException exception){
                // clear any session that you were on
                VaadinSession.getCurrent().getSession().invalidate();
                VaadinSession.getCurrent().close();
            }
        });
    }

    // configure Accordion component
    private void configureAccordion(){
        AccordionPanel register = this.noAccOptions.add("... register a new account", new HorizontalLayout(new Span("Register a new account here")));
        register.addThemeVariants(DetailsVariant.FILLED);

        AccordionPanel demoAccs = this.noAccOptions.add("... play around with a demo?", getDemoBtns());
        demoAccs.addThemeVariants(DetailsVariant.FILLED);

        noAccOptions.setWidth("524px");

    }


    /* ------------------- GET COMPONENTS -------------------
     * */
    private HorizontalLayout getDemoBtns() {
        // demo account explanations
        TextArea userDemoExplanation = new TextArea("User Demo Account");
        userDemoExplanation.setValue("Test out a sample account of an employee named Barry! See " +
                "the tickets assigned to him and the projects he's working on.\nBarry has less privileges than an " +
                "ADMIN account.\nClick the button below to start testing Barry's account!");
        userDemoExplanation.setWidth("450px");
        userDemoExplanation.addThemeVariants(TextAreaVariant.LUMO_SMALL);

        TextArea adminDemoExplanation = new TextArea("Admin Demo Account");
        adminDemoExplanation.setValue("Test out a sample account of an employee named Admin! See " +
                "the tickets assigned to her and the projects she's working on.\nAdmin has more privileges than an " +
                "USER account. Create new projects and tickets, see a list of all your employees, and register " +
                "new employees to your account!\nClick the button below to start testing Barry's account!");
        adminDemoExplanation.setWidth("450px");
        adminDemoExplanation.addThemeVariants(TextAreaVariant.LUMO_SMALL);

        TextArea funTipExplanation = new TextArea("Fun Tip!");
        funTipExplanation.setValue("Here's a fun test you can do!" +
                "\nLogin to the Admin Demo account. Create a new project and assigned some new tickets to Barry." +
                "\nLogout and login as the User account and see your new project and tickets assigned to Barry " +
                "appear!\n\n Here are 2 other Admin and User accounts to test out!");
        funTipExplanation.setWidth("450px");
        funTipExplanation.addThemeVariants(TextAreaVariant.LUMO_SMALL);

        VerticalLayout demoDetails = new VerticalLayout(
                userDemoExplanation,
                this.userDemoBtn,
                adminDemoExplanation,
                this.adminDemoBtn,
                funTipExplanation,
                new HorizontalLayout(this.adminDemoBtn2, this.userDemoBtn2)
        );
        return new HorizontalLayout(demoDetails);
    }
}
