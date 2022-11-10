package com.bugTrackerApp.BugTrackerApp.data.repository;

import com.bugTrackerApp.BugTrackerApp.data.entity.Employee;
import com.bugTrackerApp.BugTrackerApp.data.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    @Query("select t from Ticket t " +
                 "where t.project.name = :pName"
    )
    List<Ticket> findTicketByProjectName(@Param("pName") String name);

//    @Query("select t from ticket_assigned_employees t where t.ticket.id = :ticketId")
//    List<Employee> findTicketAssignedEmployees(@Param("ticketId") UUID id);
    //@Query("select t from Ticket t where t.employee.id = :employee_id")
//@Query("select distinct t from Ticket t join t.assignedTickets e where e.employee_id = :employee_id")
//List<Ticket> findTicketsByEmployeeId(UUID employee_id);

    List<Ticket> findTicketsByEmployeeId(UUID employee_id);
}
