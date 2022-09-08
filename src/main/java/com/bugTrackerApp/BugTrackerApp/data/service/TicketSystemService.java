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
}
