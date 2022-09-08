package com.bugTrackerApp.BugTrackerApp.data.service;

import com.bugTrackerApp.BugTrackerApp.data.entity.*;
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
    // CRUD methods: count, save, delete

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

    // SAVE METHODS
    public void saveCompany(Company company) {
        if (company == null) {
            System.err.println("Company is null. Are you sure you have connected your form to the application? ");
        }
        companyRepo.save(company);
    }
    public void saveEmployee(Employee employee) {
        if (employee == null) {
            System.err.println("employee is null. Are you sure you have connected your form to the application? ");
        }
        employeeRepo.save(employee);
    }
    public void saveDepartment(Department department) {
        if (department == null) {
            System.err.println("department is null. Are you sure you have connected your form to the application? ");
        }
        departmentRepo.save(department);
    }
    public void saveSecurityClearance(SecurityClearance securityClearance) {
        if (securityClearance == null) {
            System.err.println("Security Clearance is null. Are you sure you have connected your form to the application? ");
        }
        securityClearanceRepo.save(securityClearance);
    }
    public void saveAccoutStatus(AccountStatus accountStatus) {
        if (accountStatus == null) {
            System.err.println("Account Status is null. Are you sure you have connected your form to the application? ");
        }
        accountStatusRepo.save(accountStatus);
    }

    // DELETE METHODS
    public void deleteCompany(Company company) {
        companyRepo.delete(company);
    }
    public void deleteEmployee(Employee employee) {
        employeeRepo.delete(employee);
    }
    public void deleteDepartment(Department department) {
        departmentRepo.delete(department);
    }
    public void deleteSecurityClearance(SecurityClearance securityClearance ) {
        securityClearanceRepo.delete(securityClearance);
    }
    public void deleteAccountStatus(AccountStatus accountStatus) {
        accountStatusRepo.delete(accountStatus);
    }




}
