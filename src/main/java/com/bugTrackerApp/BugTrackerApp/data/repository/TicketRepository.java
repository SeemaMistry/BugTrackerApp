package com.bugTrackerApp.BugTrackerApp.data.repository;

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

    List<Ticket> findTicketsAssignedToEmployeesByEmployeesAssignedToTicketId(UUID id);

    List<Ticket> findTicketsAssignedToEmployeesByEmployeesAssignedToTicketIdAndProjectId(UUID eId, UUID pId);

}
