package com.bugTrackerApp.BugTrackerApp.data.repository;

import com.bugTrackerApp.BugTrackerApp.data.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    @Query("select e from Employee e " +
            "where lower(e.firstName) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(e.lastName) like lower(concat('%', :searchTerm, '%'))"
    )
    List<Employee> search(@Param("searchTerm") String searchTerm);
}
