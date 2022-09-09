package com.bugTrackerApp.BugTrackerApp.views.AdminViews;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;

public class TicketForm extends FormLayout {
    public TicketForm() {
        H1 welcome = new H1("Edit an ticket's info here");
        add(welcome);
    }
}
