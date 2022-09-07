package com.bugTrackerApp.BugTrackerApp.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "project_id"))
})
public class Project extends AbstractEntity{
    @NotBlank
    @Column(unique = true)
    private String name;

    @NotBlank
    private String description;

    @CreationTimestamp
    private Timestamp createdDate;

    @UpdateTimestamp
    private Timestamp updateDate;

    @ManyToOne
    @JoinColumn(name = "creator_employee_id")
    @NotNull
    // TODO: add json ignore properties
    @JsonIgnoreProperties()
    private Employee creator_employee;


    // TODO: M:N problem with project and invited_employees
//    @OneToMany(mappedBy = "employee")
//    @Nullable
//    private List<Employee> invited_employees  = new LinkedList<>();

    @OneToMany(mappedBy = "project")
    @Nullable
    private List<Ticket> tickets = new LinkedList<>();


    @ManyToOne
    @JoinColumn(name = "status_id")
    @NotNull
    private Status projectStatus;

    @ManyToMany(mappedBy = "invitedProjects")
    private Set<Employee> employeeList;

}