package com.bugTrackerApp.BugTrackerApp.views.AdminViews;

import com.bugTrackerApp.BugTrackerApp.data.entity.*;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.List;
import java.util.stream.Collectors;

public class TicketForm extends FormLayout {
    // Components: TextField, Multiselect ComboBox, ComboBox, Buttons
    TextField subject = new TextField("Ticket Subject");
    DatePicker dueDate = new DatePicker("Due Date");
    ComboBox<Employee> ticketReporter = new ComboBox<>("Ticket Reporter");
    ComboBox<TicketPriority> ticketPriority = new ComboBox<>("Priority");
    ComboBox<TicketEstimatedTime> ticketEstimatedTime = new ComboBox<>("Estimated Time");
    ComboBox<TicketType> ticketType = new ComboBox<>("Type");
    ComboBox<Status> ticketStatus = new ComboBox<>("Status");
    MultiSelectComboBox<Employee> ticketsAssignedToEmployees = new MultiSelectComboBox<>("Assigned Employees");
    TextArea assignedEmployees = new TextArea("Assigned Employees");
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    FormLayout formLayout = new FormLayout();

    // Entity objects
    Ticket ticket;
    Project project;

    // bean Binder
    Binder<Ticket> binder = new BeanValidationBinder<>(Ticket.class);

    public TicketForm(
            List<Employee> employeeList,
            List<TicketPriority> ticketPriorityList,
            List<TicketEstimatedTime> ticketEstimatedTimeList,
            List<TicketType> ticketTypeList,
            List<Status> statuses
    ) {
        // bind instance fields
        binder.bindInstanceFields(this);

        // configure components
        ticketReporter.setItems(employeeList);
        ticketReporter.setItemLabelGenerator(Employee::getFullName);
        ticketPriority.setItems(ticketPriorityList);
        ticketPriority.setItemLabelGenerator(TicketPriority::getName);
        ticketEstimatedTime.setItems(ticketEstimatedTimeList);
        ticketEstimatedTime.setItemLabelGenerator(TicketEstimatedTime::getDescription);
        ticketType.setItems(ticketTypeList);
        ticketType.setItemLabelGenerator(TicketType::getName);
        ticketStatus.setItems(statuses);
        ticketStatus.setItemLabelGenerator(Status::getName);

        ticketsAssignedToEmployees.setItems(employeeList);
        ticketsAssignedToEmployees.setItemLabelGenerator(Employee::getFullName);
        assignedEmployees.setReadOnly(true);
        configureMultiSelectComboBox();

        // configure Form styling
        configureForm();

        VerticalLayout form = new VerticalLayout(getForm(), createButtonLayout());
        form.getStyle()
                .set("margin-top", "0px")
                .set("padding-top", "0px");

        // render to screen
        add(form);
    }

    private void configureForm() {
       formLayout.add(subject,
                dueDate,
                ticketReporter,
                ticketPriority,
                ticketEstimatedTime,
                ticketType,
                ticketStatus,
               ticketsAssignedToEmployees,
               assignedEmployees
                );

        formLayout.setColspan(subject, 3);
        formLayout.setColspan(assignedEmployees,3);
        formLayout.setColspan(ticketsAssignedToEmployees, 2);
        formLayout.setResponsiveSteps(
                new ResponsiveStep("0", 1),
                new ResponsiveStep("500px", 3)
        );
    }

    private FormLayout getForm() {
        return formLayout;
    }

    // stream employees selected from multiselect comboBox as a string to TextArea
    private void configureMultiSelectComboBox() {
        // map full names of employees selected to read only TextArea
        ticketsAssignedToEmployees.addValueChangeListener(e -> {
            String assignedEmployeesText = e.getValue()
                    .stream()
                    .map(Employee::getFullName)
                    .collect(Collectors.joining(", "));
            assignedEmployees.setValue(assignedEmployeesText);
        });
    }

    // button layout and click events
    private HorizontalLayout createButtonLayout() {
        // set button colors
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        // fire events
        save.addClickListener(e -> validateAndSave());
        delete.addClickListener(e -> fireEvent(new TicketForm.DeleteEvent(this, ticket)));
        close.addClickListener(e -> fireEvent(new TicketForm.CloseEvent(this)));

        return new HorizontalLayout(save, delete, close);
    }

    // validate and save ticket
    private void validateAndSave() {
        try {
            binder.writeBean(ticket);
            fireEvent(new TicketForm.SaveEvent(this, ticket));
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }
    }

    // set a ticket to the form through readBean()
    public void setTicket(Ticket ticket) {
        // set ticket through readBean
        this.ticket = ticket;
        binder.readBean(ticket);
    }

    // retrieve selected employees from multiselect comboBox


    // EVENT BUS - VAADIN COMPONENT EVENTS
    public static abstract class ContactFormEvent extends ComponentEvent<TicketForm> {
        private Ticket ticket;

        protected ContactFormEvent(TicketForm source, Ticket ticket) {
            super(source, false);
            this.ticket = ticket;
        }

        public Ticket getTicket() {
            return ticket;
        }
    }


    public static class SaveEvent extends TicketForm.ContactFormEvent {
        SaveEvent(TicketForm source, Ticket ticket) {
            super(source, ticket);
        }
    }

    public static class DeleteEvent extends TicketForm.ContactFormEvent {
        DeleteEvent(TicketForm source, Ticket ticket) {
            super(source, ticket);
        }

    }

    public static class CloseEvent extends TicketForm.ContactFormEvent {
        CloseEvent(TicketForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }


}
