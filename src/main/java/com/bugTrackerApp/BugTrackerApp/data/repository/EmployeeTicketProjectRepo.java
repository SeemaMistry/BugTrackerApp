package com.bugTrackerApp.BugTrackerApp.data.repository;

import com.bugTrackerApp.BugTrackerApp.data.entity.EmployeeTicketProject;
import com.bugTrackerApp.BugTrackerApp.data.entity.EmployeeTicketProjectKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeTicketProjectRepo extends JpaRepository<EmployeeTicketProject, EmployeeTicketProjectKey> {
}
