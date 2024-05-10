package com.team2.resumeeditorproject.admin.repository;

import com.team2.resumeeditorproject.resume.domain.ResumeEdit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminResumeEditRepository extends JpaRepository<ResumeEdit, Long> {
    List<ResumeEdit> findByCompany(String company);
    List<ResumeEdit> findByOccupation(String occupation);
}
