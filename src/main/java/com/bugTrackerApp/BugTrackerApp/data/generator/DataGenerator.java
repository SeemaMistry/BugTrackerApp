package com.bugTrackerApp.BugTrackerApp.data.generator;

import com.bugTrackerApp.BugTrackerApp.data.entity.Company;
import com.bugTrackerApp.BugTrackerApp.data.entity.Department;
import com.bugTrackerApp.BugTrackerApp.data.entity.SecurityClearance;
import com.bugTrackerApp.BugTrackerApp.data.repository.CompanyRepository;
import com.bugTrackerApp.BugTrackerApp.data.repository.DepartmentRepository;
import com.bugTrackerApp.BugTrackerApp.data.repository.SecurityClearanceRepository;
import com.vaadin.exampledata.DataType;
import com.vaadin.exampledata.ExampleDataGenerator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(CompanyRepository companyRepo,
                                      DepartmentRepository departmentRepo,
                                      SecurityClearanceRepository securityClearanceRepo
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





//            int seed = 123;
//
//            // create 5 companies
//            ExampleDataGenerator<Company> companyGenerator = new ExampleDataGenerator<>(Company.class, LocalDateTime.now());
//            companyGenerator.setData(Company::setName, DataType.COMPANY_NAME);
//            List<Company> companies = companyRepo.saveAll(companyGenerator.create(5, seed));
//
//            // set departments
//            List<Department> statuses = statusRepository
//                    .saveAll(Stream.of("Imported lead", "Not contacted", "Contacted", "Customer", "Closed (lost)")
//                            .map(Status::new).collect(Collectors.toList()));


        };
    }
}
