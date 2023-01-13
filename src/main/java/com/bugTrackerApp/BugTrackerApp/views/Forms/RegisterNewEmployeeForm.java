package com.bugTrackerApp.BugTrackerApp.views.Forms;

import com.bugTrackerApp.BugTrackerApp.data.entity.Company;
import com.bugTrackerApp.BugTrackerApp.data.entity.SecurityClearance;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.util.List;

public class RegisterNewEmployeeForm extends EmployeeForm{
    TextField username = new TextField("Username");
    TextField password = new TextField("Password");

    public RegisterNewEmployeeForm(List<Company> companies, List<SecurityClearance> securityClearances) {
        super(companies, securityClearances);
        updateUsernameAndPassword();
        add(username, password);

    }

    private void updateUsernameAndPassword(){

        username.setValueChangeMode(ValueChangeMode.EAGER);

        password.setValueChangeMode(ValueChangeMode.EAGER);
        username.setValue(super.firstName.getValue());
        password.setValue(super.firstName.getValue());
    }
}
