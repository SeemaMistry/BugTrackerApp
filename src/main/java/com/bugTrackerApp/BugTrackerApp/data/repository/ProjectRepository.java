package com.bugTrackerApp.BugTrackerApp.data.repository;

import com.bugTrackerApp.BugTrackerApp.data.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
    Project findByName(String name);

    @Query("select p from Project p " +
            "where lower(p.name) like lower(concat('%', :searchTerm, '%'))")
    List<Project> searchProject(@Param("searchTerm") String searchTerm);
}
