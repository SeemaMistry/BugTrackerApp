package com.bugTrackerApp.BugTrackerApp.data.generator;

import com.bugTrackerApp.BugTrackerApp.data.entity.*;
import com.bugTrackerApp.BugTrackerApp.data.repository.*;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;


@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(CompanyRepository companyRepo,
                                      DepartmentRepository departmentRepo,
                                      SecurityClearanceRepository securityClearanceRepo,
                                      AccountStatusRepository accountStatusRepo,
                                      TicketPriorityRepository ticketPriorityRepo,
                                      TicketEstimatedTimeRepository ticketEstimatedTimeRepo,
                                      TicketTypeRepository ticketTypeRepo,
                                      StatusRepository statusRepo
    )  {
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            logger.info("Generating demo data");

            List<Company> companies = Arrays.asList(
                    new Company("company #1"),
                    new Company("company #2"),
                    new Company("Company #3")
            );
            companyRepo.saveAll(companies);

            List<Department> departments = Arrays.asList(
                    new Department(companies.get(0), "HR"),
                    new Department(companies.get(0),"Software"),
                    new Department(companies.get(0),"Sales")
            );
            departmentRepo.saveAll(departments);

            List<SecurityClearance> securityClearances = Arrays.asList(
                    new SecurityClearance(companies.get(0), "Guest", "L0"),
                    new SecurityClearance(companies.get(0), "Developer", "L1"),
                    new SecurityClearance(companies.get(0), "Admin", "L2")
            );
            securityClearanceRepo.saveAll(securityClearances);

            List<AccountStatus> accountStatuses = Arrays.asList(
                    new AccountStatus("ACTIVE"),
                    new AccountStatus("LOCKED"),
                    new AccountStatus("CLOSED")
            );
            accountStatusRepo.saveAll(accountStatuses);

            List<TicketEstimatedTime> ticketEstimatedTimes = Arrays.asList(
                    new TicketEstimatedTime(1, "can finish task in 15-30 minutes"),
                    new TicketEstimatedTime(2, "can finish task in 1-2 hours"),
                    new TicketEstimatedTime(4, "can finish task in 1/2 a day to 1 day"),
                    new TicketEstimatedTime(8, "can finish task in 2 days"),
                    new TicketEstimatedTime(16, "can finish task in 3-5 days"),
                    new TicketEstimatedTime(32, "can finish task in 1 week"),
                    new TicketEstimatedTime(64, "can finish task in 1 month")
            );
            ticketEstimatedTimeRepo.saveAll(ticketEstimatedTimes);

            List<TicketType> ticketTypes = Arrays.asList(
                    new TicketType("Feature"),
                    new TicketType("Bug"),
                    new TicketType("Documentation"),
                    new TicketType("Database"),
                    new TicketType("Styling")
            );
            ticketTypeRepo.saveAll(ticketTypes);

            List<TicketPriority> ticketPriorities = Arrays.asList(
                    new TicketPriority("High"),
                    new TicketPriority("Medium"),
                    new TicketPriority("Low")
            );
            ticketPriorityRepo.saveAll(ticketPriorities);

            List<Status> statuses = Arrays.asList(
                    new Status("In Progress", "it is being worked on"),
                    new Status("Open", "it is open for people to apply to"),
                    new Status("Closed", "task is closed")
            );
            statusRepo.saveAll(statuses);

        };
    }
}
