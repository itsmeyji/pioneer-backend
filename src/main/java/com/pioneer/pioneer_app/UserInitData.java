package com.pioneer.pioneer_app;

import com.pioneer.pioneer_app.attendance.AttendanceRepository;
import com.pioneer.pioneer_app.attendance.Attendance;
import com.pioneer.pioneer_app.users.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;
import com.pioneer.pioneer_app.attendance.Attendance.Status;

@Configuration
public class UserInitData {
    @Bean
    CommandLineRunner seed(UserRepository users, AttendanceRepository attendances) {
        return args -> {
            if (users.count() == 0) {
                // 관리자 세팅
                User admin1 = new User();
                admin1.setUsername("admin1");
                admin1.setPassword("1234");
                admin1.setName("관리자1");
                admin1.setEmail("admin1@gmail.com");
                admin1.setGrade(4);
                admin1.setRole("admin");
                admin1.setPosition(User.Position.MASTER);
                admin1.setDepartment("컴퓨터과학과");
                admin1.setStudentNumber("00000001");
                users.save(admin1);

                User admin2 = new User();
                admin2.setUsername("admin2");
                admin2.setPassword("1234");
                admin2.setName("관리자2");
                admin2.setEmail("admin2@gmail.com");
                admin2.setGrade(4);
                admin2.setRole("admin");
                admin2.setPosition(User.Position.UNDERGRADUATE); // ⚠️ 수정: OTHER -> UNDERGRADUATE
                admin2.setDepartment("핀빅스");
                admin2.setStudentNumber("00000002");
                users.save(admin2);

                // 학생 3명 세팅
                User stu1 = new User();
                stu1.setUsername("student1");
                stu1.setPassword("1234");
                stu1.setName("학생1");
                stu1.setEmail("s1@gmail.com");
                stu1.setGrade(1);
                stu1.setRole("user");
                stu1.setPosition(User.Position.UNDERGRADUATE);
                stu1.setDepartment("핀빅스");
                stu1.setStudentNumber("20201234");
                users.save(stu1);

                User stu2 = new User();
                stu2.setUsername("student2");
                stu2.setPassword("1234");
                stu2.setName("학생2");
                stu2.setEmail("s2@gmail.com");
                stu2.setGrade(2);
                stu2.setRole("user");
                stu2.setPosition(User.Position.UNDERGRADUATE);
                stu2.setDepartment("핀빅스");
                stu2.setStudentNumber("20205678");
                users.save(stu2);

                User stu3 = new User();
                stu3.setUsername("student3");
                stu3.setPassword("1234");
                stu3.setName("학생3");
                stu3.setEmail("s3@gmail.com");
                stu3.setGrade(3);
                stu3.setRole("user");
                stu3.setPosition(User.Position.UNDERGRADUATE);
                stu3.setDepartment("컴퓨터과학과");
                stu3.setStudentNumber("20207890");
                users.save(stu3);

                // ✅ 추가: 출석 데이터 초기화
                LocalDate today = LocalDate.now();

                Attendance a1 = new Attendance();
                a1.setDate(today.minusDays(1)); // 어제
                a1.setUser(stu1);
                a1.setStatus(Status.ATTEND);
                attendances.save(a1);

                Attendance a2 = new Attendance();
                a2.setDate(today.minusDays(1)); // 어제
                a2.setUser(stu2);
                a2.setStatus(Status.ABSENT);
                a2.setReason("개인 사정");
                attendances.save(a2);

                Attendance a3 = new Attendance();
                a3.setDate(today); // 오늘
                a3.setUser(stu1);
                a3.setStatus(Status.UNKNOWN);
                attendances.save(a3);
            }
        };
    }
}

    //관리자
    //admin1 : 컴퓨터과학과, 학번 00000001, MASTER(석박사)
    //admin2 : 핀빅스, 학번 00000002, UNDERGRADUATE(학부)
    //
    //학생 (user)
    //student1 : 핀빅스, 학번 20201234, UNDERGRADUATE
    //student2 : 핀빅스, 학번 20205678, UNDERGRADUATE
    //student3 : 컴퓨터과학과, 학번 20207890, UNDERGRADUATE
