package com.bugTrackerApp.BugTrackerApp.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Data
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "department_id"))
})
public class Department extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "company_id")
    @NotNull
    @JsonIgnoreProperties({"employees"})
    private Company company;

    @NotEmpty
    private String name = "";
}
