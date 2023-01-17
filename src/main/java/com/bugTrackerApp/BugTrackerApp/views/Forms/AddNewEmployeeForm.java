package com.bugTrackerApp.BugTrackerApp.views.Forms;

import com.bugTrackerApp.BugTrackerApp.data.entity.SecurityClearance;
import com.bugTrackerApp.BugTrackerApp.data.service.UserRelationsService;
import com.vaadin.flow.component.textfield.TextField;

import java.util.List;

public class AddNewEmployeeForm extends EmployeeForm{
    TextField username = new TextField("Username");
    TextField password = new TextField("Password");

    public AddNewEmployeeForm(List<SecurityClearance> securityClearances, UserRelationsService URService) {
        super(securityClearances, URService);
        updateUsernameAndPassword();
        add(username, password);
        updateUsernameAndPassword();

    }

    private void updateUsernameAndPassword(){
//        super.firstName.setValueChangeMode(ValueChangeMode.ON_CHANGE);
        super.firstName.addValueChangeListener(e -> {
            // when value changes, set it as username and password
            username.setValue(e.getValue());
            password.setValue(e.getValue());
        });
    }

}
