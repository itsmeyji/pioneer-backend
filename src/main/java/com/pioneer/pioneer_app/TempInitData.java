package com.pioneer.pioneer_app;

import com.pioneer.pioneer_app.attendance.AttendanceRepository;
import com.pioneer.pioneer_app.attendance.Attendance;
import com.pioneer.pioneer_app.users.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;


@Configuration
public class TempInitData {
    @Bean
    CommandLineRunner seed(UserRepository users, AttendanceRepository attendances) {
        return args -> {
            if (users.count() == 0) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword("1234");
                admin.setName("관리자");
                admin.setEmail("admin@gmail.com");
                admin.setGrade(4);
                admin.setRole("admin");
                users.save(admin);

                User stu = new User();
                stu.setUsername("student1");
                stu.setPassword("1234");
                stu.setName("학생1");
                stu.setEmail("s1@gmail.com");
                stu.setGrade(2);
                stu.setRole("user");
                users.save(stu);
            }

            // student1의 실제 userId 조회해서 찾아서 쓰기
            Long student1Id = users.findByUsername("student1")
                    .orElseThrow(() -> new IllegalStateException("student1 not found"))
                    .getUserId();

            // 출석 샘플 데이터
            if (attendances.count() == 0) {
                Attendance a1 = new Attendance();
                a1.setUserId(2L); // student1 ID
                a1.setDate(LocalDate.now());
                a1.setStatus("참석");
                a1.setReason(null);
                attendances.save(a1);

                Attendance a2 = new Attendance();
                a2.setUserId(2L);
                a2.setDate(LocalDate.now().plusDays(1));
                a2.setStatus("불참");
                a2.setReason("가족 여행");
                attendances.save(a2);

                Attendance a3 = new Attendance();
                a3.setUserId(2L);
                a3.setDate(LocalDate.now().plusDays(2));
                a3.setStatus("미정");
                a3.setReason(null);
                attendances.save(a3);
            }
            ;
        };
    }
}