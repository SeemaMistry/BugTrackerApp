package com.bugTrackerApp.BugTrackerApp.data.service;

import com.bugTrackerApp.BugTrackerApp.data.entity.*;
import com.bugTrackerApp.BugTrackerApp.data.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    // COUNT METHODS
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

    // SAVE METHODS
    public void saveProject(Project project) {
        if (project == null) {
            System.err.println("Project is null. Are you sure you have connected your form to the application? ");
        }
        projectRepo.save(project);
    }
    public void saveTicket(Ticket ticket) {
        if (ticket == null) {
            System.err.println("ticket is null. Are you sure you have connected your form to the application? ");
        }
        ticketRepo.save(ticket);
    }
    public void saveStatus(Status status) {
        if (status == null) {
            System.err.println("status is null. Are you sure you have connected your form to the application? ");
        }
        statusRepo.save(status);
    }
    public void saveTicketEstimatedTime(TicketEstimatedTime ticketEstimatedTime) {
        if (ticketEstimatedTime == null) {
            System.err.println("TicketEstimatedTime is null. Are you sure you have connected your form to the application? ");
        }
        ticketEstimatedTimeRepo.save(ticketEstimatedTime);
    }
    public void saveTicketPriority(TicketPriority ticketPriority) {
        if (ticketPriority == null) {
            System.err.println("TicketPriority is null. Are you sure you have connected your form to the application? ");
        }
        ticketPriorityRepo.save(ticketPriority);
    }
    public void saveTicketType(TicketType ticketType) {
        if (ticketType == null) {
            System.err.println("TicketType is null. Are you sure you have connected your form to the application? ");
        }
        ticketTypeRepo.save(ticketType);
    }

    // DELETE METHOD
    public void deleteProject(Project project) {
        projectRepo.delete(project);
    }
    public void deleteTicket(Ticket ticket) {
        ticketRepo.delete(ticket);
    }
    public void deleteStatus(Status status) {
        statusRepo.delete(status);
    }
    public void deleteTicketEstimatedTime(TicketEstimatedTime ticketEstimatedTime) {
        ticketEstimatedTimeRepo.delete(ticketEstimatedTime);
    }
    public void deleteTicketPriority(TicketPriority ticketPriority) {
        ticketPriorityRepo.delete(ticketPriority);
    }
    public void deleteTicketType(TicketType ticketType) {
        ticketTypeRepo.delete(ticketType);
    }

    // FINDALL METHODS
    public List<Project> findAllProjects(){
        return projectRepo.findAll();
    }
    public List<Ticket> findAllTickets(String name){
        if (name == null) {
            return ticketRepo.findAll();
        } else {
            return ticketRepo.findTicketByProjectName(name);
        }
    }
    public List<Status> findAllStatuses(){
        return statusRepo.findAll();
    }
    public List<TicketEstimatedTime> findAllTicketEstimatedTimes(){
        return ticketEstimatedTimeRepo.findAll();
    }
    public List<TicketPriority> findAllTicketPriority(){
        return ticketPriorityRepo.findAll();
    }
    public List<TicketType> findAllTicketType(){
        return ticketTypeRepo.findAll();
    }

    // CUSTOM QUERIES

    // find all tickets assigned to employee (regardless of project)
    public List<Ticket> findTicketsAssignedToEmployee(Employee e){
        if (e != null) {
            return ticketRepo.findTicketsAssignedToEmployeesByEmployeesAssignedToTicketId(e.getId());
        } else {
            return new ArrayList<>();
        }
    }

    // find project by name
    public Project findProjectByName(String name) {
        return projectRepo.findByName(name);
    }

    // search for all tickets in project by employee
    public List<Ticket> searchTicketByProjectAndEmployee(Project p, Employee e) {
        if(e != null){
            return ticketRepo.findTicketsAssignedToEmployeesByEmployeesAssignedToTicketIdAndProjectId(e.getId(), p.getId());
        } else {
            return new ArrayList<>();
        }
    }

    // search for ticket(s) by subject and project
    public List<Ticket> searchTicketBySubjectAndProject(String subject, Project project){
        if (subject.isEmpty()) {
            return findAllTickets(project.getName());
        } else {
            return ticketRepo.searchTickets(subject, project.getId());
        }
    }

    // find project by project name through like searchTerm
    public List<Project> searchProjectByLikeName(String name) { return projectRepo.searchProject(name); }

    // check if employee is assigned to this project
    public boolean isEmployeeAssignedToProject(Employee e, Project p){
        return projectRepo.existsProjectsAssignedToEmployeesByEmployeesAssignedToProjectIdAndId(e.getId(), p.getId());
    }

    // get ticket by ID
    public Ticket getTicketByTicketId(UUID ticketId){
        if (ticketId == null) {
            return null;
        } else {
            return ticketRepo.getById(ticketId);
        }
    }

    public List<Project> getAllProjectByCompany(UUID companyId){
        if (projectRepo.findByCompanyId(companyId).size() == 0){
            return new ArrayList<Project>();
        } else {
            return projectRepo.findByCompanyId(companyId);
        }
    }
}
