package com.pioneer.pioneer_app;

import com.pioneer.pioneer_app.attendance.Attendance;
import com.pioneer.pioneer_app.attendance.AttendanceRepository;
import com.pioneer.pioneer_app.users.User;
import com.pioneer.pioneer_app.users.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class AttendanceInitData {
    // student1 기준 출석 자료 3건 입력
    @Bean
    CommandLineRunner seedAttendances(UserRepository users, AttendanceRepository attendances) {
        return args -> {
            if (attendances.count() == 0) {
                // student1 유저 객체 직접 가져오기
                User student1 = users.findByUsername("student1")
                        .orElseThrow(() -> new IllegalStateException("student1 not found"));

                Attendance a1 = new Attendance();
                a1.setUser(student1);
                a1.setDate(LocalDate.now());
                a1.setStatus(Attendance.Status.ATTEND);
                attendances.save(a1);

                Attendance a2 = new Attendance();
                a2.setUser(student1);
                a2.setDate(LocalDate.now().plusDays(1));
                a2.setStatus(Attendance.Status.ABSENT);
                a2.setReason("가족 여행");
                attendances.save(a2);

                Attendance a3 = new Attendance();
                a3.setUser(student1);
                a3.setDate(LocalDate.now().plusDays(2));
                a3.setStatus(Attendance.Status.UNKNOWN);
                attendances.save(a3);
            }
        };
    }


    // student1의 파일 실행 당일 날짜와 이후 2일간의 출석데이터 입력
    // 참석, 불참 (사유 : 가족 여행), 미정
}
