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

    @Query("select t from Ticket t where lower(t.subject) like lower(concat('%', :searchTerm, '%')) and t.project.id = :pid")
    List<Ticket> searchTickets(@Param("searchTerm") String searchTerm,
                               @Param("pid") UUID projectId);
    List<Ticket> findTicketsAssignedToEmployeesByEmployeesAssignedToTicketId(UUID id);

    List<Ticket> findTicketsAssignedToEmployeesByEmployeesAssignedToTicketIdAndProjectId(UUID eId, UUID pId);

}
