package com.bugTrackerApp.BugTrackerApp.data.service;

import com.bugTrackerApp.BugTrackerApp.data.entity.*;
import com.bugTrackerApp.BugTrackerApp.data.repository.*;
import com.vaadin.flow.server.VaadinSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserRelationsService {
    // fields
    private CompanyRepository companyRepo;
    private EmployeeRepository  employeeRepo;
    private DepartmentRepository departmentRepo;
    private SecurityClearanceRepository securityClearanceRepo;
    private AccountStatusRepository accountStatusRepo;
    private UserRepository userRepo;

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

    public void saveUser(User user) {
        if (user == null) {
            System.err.println("user is null. Are you sure you have connected your form to the application? ");
        }
        userRepo.save(user);
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

    // FINDALL METHODS
    public List<Company> findAllCompanies(String filterText) {
        if(filterText == null || filterText.isEmpty()) {
            return companyRepo.findAll();
        } else {
           return companyRepo.search(filterText);
        }
    }
    public List<Department> findAllDepartments(){
        return departmentRepo.findAll();
    }
//    public List<Employee> findAllEmployees(String filterText){
//        if (filterText == null || filterText.isEmpty()) {
//            return employeeRepo.findAll();
//        } else {
//            return employeeRepo.search(filterText);
//        }
//
//    }

    public List<Employee> findAllEmployeesByCompany(String filterText, UUID companyId){
        if (filterText == null || filterText.isEmpty()) {
            return employeeRepo.findByCompanyId(companyId);
        } else {
            return employeeRepo.searchEmployeeByCompany(filterText, companyId);
        }

    }

    public List<SecurityClearance> findAllSecurityClearances(){
        return securityClearanceRepo.findAll();
    }
    public List<AccountStatus> findAllAccountStatuses(){
        return accountStatusRepo.findAll();
    }

    // CUSTOM QUERIES

    // find all employees assigned to a project
    public List<Employee> findAllEmployeesAssignedToProject(Project p) {
        if (p == null || p.getEmployeesAssignedToProject().size() == 0) {
            return findAllEmployeesByCompany(null, VaadinSession.getCurrent().getAttribute(Company.class).getId());
        } else {
            return employeeRepo.findProjectsAssignedToEmployeesByProjectsAssignedToEmployeeId(p.getId());
        }
    }


    // exists checks
    public boolean companyExistByName(String name){
        return companyRepo.existsByName(name);
    }

    public boolean userExistsByUsername(String username) {
        return userRepo.existsByUsername(username);
    }

    public boolean employeeExistsBy(String email) {
        return employeeRepo.existsByEmail(email);
    }

    public SecurityClearance findSecurityClearanceByTitle(String title){
        return securityClearanceRepo.findBySecurityTitle(title);
    }
}
