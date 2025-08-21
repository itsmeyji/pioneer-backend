package com.pioneer.pioneer_app.attendance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByUserUserId(Long userId);
    List<Attendance> findByDate(LocalDate date);
}