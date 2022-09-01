package com.bugTrackerApp.BugTrackerApp.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.util.LinkedList;

@Entity
@Getter
@Setter
public class Company extends AbstractEntity{
    @NotBlank
    private String name;

    @OneToMany(mappedBy = "company")
    @Nullable
    private List<Employee> employees = new LinkedList<>();
}
