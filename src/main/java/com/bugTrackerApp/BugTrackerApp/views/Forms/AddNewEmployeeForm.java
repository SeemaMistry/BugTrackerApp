package com.bugTrackerApp.BugTrackerApp.views.Forms;

import com.bugTrackerApp.BugTrackerApp.data.entity.SecurityClearance;
import com.bugTrackerApp.BugTrackerApp.data.service.UserRelationsService;
import com.vaadin.flow.component.textfield.TextField;

import java.util.List;
import java.util.Random;

public class AddNewEmployeeForm extends EmployeeForm{
    TextField username = new TextField("Username");
    TextField password = new TextField("Password");

    public AddNewEmployeeForm(List<SecurityClearance> securityClearances, UserRelationsService URService) {
        super(securityClearances, URService);
        updateUsernameAndPassword();
        add(username, password);
        updateUsernameAndPassword();

    }


    /* ------------------- CONFIGURATIONS -------------------
     * */

    // dynamically set Username and Password based on firstname values from super
    private void updateUsernameAndPassword(){
        super.firstName.addValueChangeListener(e -> {
            // generate random number to add to username
            Random rand = new Random();
            // when value changes, set it as username and password
            username.setValue(e.getValue() + String.valueOf(rand.nextInt(1000)));
            password.setValue(e.getValue());
        });
    }

}
