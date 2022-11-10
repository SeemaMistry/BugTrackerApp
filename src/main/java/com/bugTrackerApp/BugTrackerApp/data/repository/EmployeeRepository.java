package com.bugTrackerApp.BugTrackerApp.data.repository;

import com.bugTrackerApp.BugTrackerApp.data.entity.Employee;
import com.bugTrackerApp.BugTrackerApp.data.entity.Ticket;
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

//    @Query("select distinct e from Employee e join e.ticketList t where t.ticket_id = :ticket_id")
//    List<Employee> findEmployeesByTicketId(UUID ticket_id);

    List<Employee> findEmployeesByTicketId(UUID ticket_id);
//    @Query("select employee_id from ticket_assigned_employees where ticket_id = :ticket_id")
//    List<Employee> findStuff(@Param("ticket_id") UUID ticket_id);
}
