package com.bugTrackerApp.BugTrackerApp.data.service;

import com.bugTrackerApp.BugTrackerApp.data.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserRelationsService {
    // fields
    private CompanyRepository companyRepo;
    private EmployeeRepository  employeeRepo;
    private DepartmentRepository departmentRepo;
    private SecurityClearanceRepository securityClearanceRepo;
    private AccountStatusRepository accountStatusRepo;

    // methods
}
