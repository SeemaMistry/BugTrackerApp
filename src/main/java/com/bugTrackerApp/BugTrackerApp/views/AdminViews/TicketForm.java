package com.bugTrackerApp.BugTrackerApp.views.AdminViews;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;

public class TicketForm extends FormLayout {
    // Components: TextField, Multiselect ComboBox, ComboBox, Buttons

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
