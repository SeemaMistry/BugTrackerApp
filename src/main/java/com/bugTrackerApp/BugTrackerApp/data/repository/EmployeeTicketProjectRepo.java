package com.bugTrackerApp.BugTrackerApp.data.repository;

import com.bugTrackerApp.BugTrackerApp.data.entity.EmployeeTicketProject;
import com.bugTrackerApp.BugTrackerApp.data.entity.EmployeeTicketProjectKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeTicketProjectRepo extends JpaRepository<EmployeeTicketProject, EmployeeTicketProjectKey> {
    // find all ETP ROWS by project and employee ids (to get all tickets assigned to employee X)

    // find all ETP ROWS by project and ticket ids (to get employees assigned to ticket Y)

    // find project by ticket and employee ids (find out which project the ticket is from)

    // find projects by employee ids (finds all the projects Employee X is working on tickets for)
}
