package com.bugTrackerApp.BugTrackerApp.data.repository;

import com.bugTrackerApp.BugTrackerApp.data.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    // search for like employees based on first or last name
    @Query("select e from Employee e " +
            "where lower(e.firstName) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(e.lastName) like lower(concat('%', :searchTerm, '%'))"
    )
    List<Employee> search(@Param("searchTerm") String searchTerm);

    // find all employees assigned to a project (in M:N table="ProjectsAssignedToEmployees")
    List<Employee> findProjectsAssignedToEmployeesByProjectsAssignedToEmployeeId(UUID id);

    // find all employees in a specified company
    List<Employee> findByCompanyId(UUID companyId);

    // search for like employees based on first or last name
    @Query("select e from Employee e " +
            "where e.company.id = :companyId " +
            "and (lower(e.firstName) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(e.lastName) like lower(concat('%', :searchTerm, '%')))"
    )
    List<Employee> searchEmployeeByCompany(@Param("searchTerm") String searchTerm, @Param("companyId") UUID companyId);

}
