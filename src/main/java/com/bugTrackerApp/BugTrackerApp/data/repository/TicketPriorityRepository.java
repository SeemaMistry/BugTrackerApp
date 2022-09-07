package com.bugTrackerApp.BugTrackerApp.data.repository;

import com.bugTrackerApp.BugTrackerApp.data.entity.TicketPriority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TicketPriorityRepository extends JpaRepository<TicketPriority, UUID> {
}
