package com.bugTrackerApp.BugTrackerApp.data.repository;

import com.bugTrackerApp.BugTrackerApp.data.entity.TicketEstimatedTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TicketEstimatedTimeRepository extends JpaRepository<TicketEstimatedTime, UUID> {
}
