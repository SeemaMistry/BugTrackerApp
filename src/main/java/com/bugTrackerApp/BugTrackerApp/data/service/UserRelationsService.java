package com.bugTrackerApp.BugTrackerApp.data.service;

import com.bugTrackerApp.BugTrackerApp.data.repository.*;
import org.springframework.stereotype.Service;

@Service
public class UserRelationsService {
    // fields
    private CompanyRepository companyRepo;
    private EmployeeRepository  employeeRepo;
    private DepartmentRepository departmentRepo;
    private SecurityClearanceRepository securityClearanceRepo;
    private AccountStatusRepository accountStatusRepo;

    // constructor
    public UserRelationsService(CompanyRepository companyRepo,
                                EmployeeRepository employeeRepo,
                                DepartmentRepository departmentRepo,
                                SecurityClearanceRepository securityClearanceRepo,
                                AccountStatusRepository accountStatusRepo) {
        this.companyRepo = companyRepo;
        this.employeeRepo = employeeRepo;
        this.departmentRepo = departmentRepo;
        this.securityClearanceRepo = securityClearanceRepo;
        this.accountStatusRepo = accountStatusRepo;
    }


    // methods
}
