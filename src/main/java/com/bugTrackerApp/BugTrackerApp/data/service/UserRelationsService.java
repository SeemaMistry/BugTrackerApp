package com.bugTrackerApp.BugTrackerApp.data.service;

import com.bugTrackerApp.BugTrackerApp.data.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserRelationsService {
    // fields
    private CompanyRepository companyRepo;
    private EmployeeRepository  employeeRepo;
    private DepartmentRepository departmentRepo;
    private SecurityClearanceRepository securityClearanceRepo;
    private AccountStatusRepository accountStatusRepo;

    // methods: findAll
    // CRUD methods: count, save, delete, update

    // COUNT METHODS
    public long countCompanies() {
        return companyRepo.count();
    }
    public long countEmployees() {
        return employeeRepo.count();
    }
    public long countDepartments() {
        return departmentRepo.count();
    }
    public long countSecurityClearances() {
        return securityClearanceRepo.count();
    }
    public long countAccountStatuses() {
        return accountStatusRepo.count();
    }




}
