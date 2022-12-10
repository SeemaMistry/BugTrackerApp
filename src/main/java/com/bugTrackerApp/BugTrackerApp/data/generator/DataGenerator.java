package com.bugTrackerApp.BugTrackerApp.data.generator;

import com.bugTrackerApp.BugTrackerApp.data.entity.*;
import com.bugTrackerApp.BugTrackerApp.data.repository.*;
import com.vaadin.exampledata.DataType;
import com.vaadin.exampledata.ExampleDataGenerator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.sql.Date;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;


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
                                      StatusRepository statusRepo,
                                      EmployeeRepository employeeRepo,
                                      ProjectRepository projectRepo,
                                      TicketRepository ticketRepo
    )  {
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());

            // create sample data to test table creation and relations for all entities
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

            // try dataGenerator
            Random r = new Random(123);
            ExampleDataGenerator<Employee> employeeExampleDataGenerator = new ExampleDataGenerator<>(Employee.class, LocalDateTime.now());
            employeeExampleDataGenerator.setData(Employee::setFirstName, DataType.FIRST_NAME);
            employeeExampleDataGenerator.setData(Employee::setLastName, DataType.LAST_NAME);
            employeeExampleDataGenerator.setData(Employee::setEmail, DataType.EMAIL);
//            employeeExampleDataGenerator.setData(Employee::setUserAccountDetail, new User(Employee::getFirstName, Employee::getLastName, Role.USER));


            // TODO: things i removed when i extracted some fields to User entity
//            employeeExampleDataGenerator.setData(Employee::setUsername, DataType.FULL_NAME); // set username as fullname for now
//            employeeExampleDataGenerator.setData(Employee::setPassword, DataType.WORD);


            List<Employee> employees = employeeExampleDataGenerator.create(10, 123).stream().map(employee -> {
                employee.setCompany(companies.get(0));

                // TODO: things i removed when i extracted some fields to User entity
//                employee.setAccountStatus(accountStatuses.get(0));

                employee.setSecurityClearance(securityClearances.get(1));
//                employee.setUserAccountDetail(new User(employee.getFirstName(), employee.getFirstName(), Role.USER));
//                employee.getUserAccountDetail().setAccountStatus(accountStatuses.get(0));

                return employee;
            }).collect(Collectors.toList());

            employeeRepo.saveAll(employees);

            List<Project> projects = Arrays.asList(
                    new Project("Grocery App", "Create a grocery list app", employees.get(0), statuses.get(0)),
                    new Project("Animals App", "Create an animal list app", employees.get(1), statuses.get(1)),
                    new Project("Plants App Project", "Create a plants list app", employees.get(2), statuses.get(2))
            );
            projectRepo.saveAll(projects);

            List<Ticket> tickets = Arrays.asList(
                    new Ticket(
                            "Button to save not working",
                            LocalDate.of(2022,9,10),
                            projects.get(0),
                            employees.get(5),
                            ticketPriorities.get(0),
                            ticketEstimatedTimes.get(0),
                            ticketTypes.get(1),
                            statuses.get(0)
                            ),
                    new Ticket(
                            "Button to save the wrong colour",
                            LocalDate.of(2022,9,10),
                            projects.get(0),
                            employees.get(5),
                            ticketPriorities.get(2),
                            ticketEstimatedTimes.get(3),
                            ticketTypes.get(4),
                            statuses.get(0)
                    ),
                    new Ticket(
                            "Add a menu bar for navigation",
                            LocalDate.of(2022,9,10),
                            projects.get(1),
                            employees.get(6),
                            ticketPriorities.get(0),
                            ticketEstimatedTimes.get(4),
                            ticketTypes.get(0),
                            statuses.get(0)
                    ),
                    new Ticket(
                            "Add a menu bar for navigation",
                            LocalDate.of(2022,9,10),
                            projects.get(2),
                            employees.get(6),
                            ticketPriorities.get(0),
                            ticketEstimatedTimes.get(4),
                            ticketTypes.get(0),
                            statuses.get(0)
                    ),
                    new Ticket(
                            "Tester of set of employees",
                            LocalDate.of(2022,9,10),
                            projects.get(2),
                            employees.get(1),
                            ticketPriorities.get(0),
                            ticketEstimatedTimes.get(4),
                            ticketTypes.get(0),
                            statuses.get(0),
                            Arrays.asList(employees.get(1))
                    ),
                    new Ticket(
                            "Tester of Barry R. List appearance",
                            LocalDate.of(2022,9,10),
                            projects.get(1),
                            employees.get(1),
                            ticketPriorities.get(0),
                            ticketEstimatedTimes.get(4),
                            ticketTypes.get(0),
                            statuses.get(0),
                            Arrays.asList(employees.get(1), employees.get(2))
                    )
            );
            ticketRepo.saveAll(tickets);

            logger.info("Generating demo data");

        };
    }
}
