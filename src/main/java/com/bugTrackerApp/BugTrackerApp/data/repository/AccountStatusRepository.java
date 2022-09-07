package com.bugTrackerApp.BugTrackerApp.data.repository;

import com.bugTrackerApp.BugTrackerApp.data.entity.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountStatusRepository extends JpaRepository<AccountStatus, UUID> {
}
