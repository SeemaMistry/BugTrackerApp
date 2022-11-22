package com.bugTrackerApp.BugTrackerApp.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "departmentId"))
})
public class Department extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "companyId")
    @NotNull
    @JsonIgnoreProperties({"employees", "departments", "securityClearances"})
    private Company company;

    @NotBlank
    private String name;

}
