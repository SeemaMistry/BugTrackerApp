package com.bugTrackerApp.BugTrackerApp.views.AdminViews;

import com.bugTrackerApp.BugTrackerApp.data.entity.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

public class TicketForm extends FormLayout {
    // Components: TextField, Multiselect ComboBox, ComboBox, Buttons
    TextField subject = new TextField("Ticket Subject");
    DatePicker dueDate = new DatePicker();
    ComboBox<Employee> ticketReporter = new ComboBox<>("Ticket Reporter");
    ComboBox<TicketPriority> ticketPriority = new ComboBox<>("Priority");
    ComboBox<TicketEstimatedTime> ticketEstimatedTime = new ComboBox<>("Estimated Time");
    ComboBox<TicketType> ticketType = new ComboBox<>("Type");
    ComboBox<Status> ticketStatus = new ComboBox<>("Status");
    MultiSelectComboBox employeesAssignedToTicket = new MultiSelectComboBox<>("Assigned Employees");
    TextArea assignedEmployees = new TextArea("Assigned Employees");
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");
    
    // Ticket object

    // bean Binder

    public TicketForm() {
        // bind instance fields

        // configure components
        H1 welcome = new H1("Edit an ticket's info here");


        // render to screen
        add(welcome);
    }

    // stream employees selected from multiselect comboBox as a string to TextArea


    // button layout and click events


    // validate and save ticket


    // set a ticket to the form through readBean()


    // retrieve selected employees from multiselect comboBox


    // EVENT BUS - VAADIN COMPONENT EVENTS


}
