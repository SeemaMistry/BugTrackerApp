package com.bugTrackerApp.BugTrackerApp.data.generator;

import com.bugTrackerApp.BugTrackerApp.data.entity.AccountStatus;
import com.bugTrackerApp.BugTrackerApp.data.entity.Company;
import com.bugTrackerApp.BugTrackerApp.data.entity.Department;
import com.bugTrackerApp.BugTrackerApp.data.entity.SecurityClearance;
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



        };
    }
}
