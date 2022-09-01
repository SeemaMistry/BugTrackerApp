package com.bugTrackerApp.BugTrackerApp.data.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
public class AccountStatus extends AbstractEntity {
    @NotEmpty
    private String name = "";
}
