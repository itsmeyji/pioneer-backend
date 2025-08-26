package com.pioneer.pioneer_app.assignments;

import com.pioneer.pioneer_app.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByAssignmentAssignmentId(Long assignmentId);
    List<Submission> findByUserAndAssignmentAssignmentId(User user, Long assignmentId);
}
