package com.bugTrackerApp.BugTrackerApp.data.repository;

import com.bugTrackerApp.BugTrackerApp.data.entity.SecurityClearance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SecurityClearanceRepository extends JpaRepository<SecurityClearance, UUID> {

    SecurityClearance findBySecurityTitle(String title);
}
