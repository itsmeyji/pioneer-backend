package com.pioneer.pioneer_app.assignments;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByType(Assignment.Type type);
}
