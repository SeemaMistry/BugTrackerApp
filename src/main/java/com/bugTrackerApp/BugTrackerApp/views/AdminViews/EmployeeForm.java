package com.bugTrackerApp.BugTrackerApp.views.AdminViews;

import com.bugTrackerApp.BugTrackerApp.data.entity.AccountStatus;
import com.bugTrackerApp.BugTrackerApp.data.entity.Company;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;

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
    public EmployeeForm(List<Company> companies, List<AccountStatus> accountStatuses) {
        // populate combo boxes
        company.setItems(companies);
        company.setItemLabelGenerator(Company::getName);
        accountStatus.setItems(accountStatuses);
        accountStatus.setItemLabelGenerator(AccountStatus::getName);

        H1 welcome = new H1("Edit an employee's info here");

        add(welcome, firstName, lastName, email, company, accountStatus);
    }
}
