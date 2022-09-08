package com.bugTrackerApp.BugTrackerApp.data.service;

import com.bugTrackerApp.BugTrackerApp.data.entity.TicketEstimatedTime;
import com.bugTrackerApp.BugTrackerApp.data.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TicketSystemService {
    ProjectRepository projectRepo;
    TicketRepository ticketRepo;
    StatusRepository statusRepo;
    TicketEstimatedTimeRepository ticketEstimatedTimeRepo;
    TicketPriorityRepository ticketPriorityRepo;
    TicketTypeRepository ticketTypeRepo;

    // method: findAll
    // CRUD methods: count, save, delete

    // FINDALL METHODS
    public long countProjects() {
        return projectRepo.count();
    }
    public long countTickets() {
        return ticketRepo.count();
    }
    public long countStatuses() {
        return statusRepo.count();
    }
    public long countTicketEstimatedTimes() {
        return ticketEstimatedTimeRepo.count();
    }
    public long countTicketPriority() {
        return ticketPriorityRepo.count();
    }
    public long countTicketTypes() {
        return ticketTypeRepo.count();
    }
}
