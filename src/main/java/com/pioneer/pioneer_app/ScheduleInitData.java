//package com.pioneer.pioneer_app;
//
//import com.pioneer.pioneer_app.schedules.Schedule;
//import com.pioneer.pioneer_app.schedules.ScheduleRepository;
//import com.pioneer.pioneer_app.users.User;
//import com.pioneer.pioneer_app.users.UserRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.time.LocalDate;
//
//@Configuration
//public class ScheduleInitData {
//
//    @Bean
//    CommandLineRunner seedSchedules(ScheduleRepository schedules, UserRepository users) {
//        return args -> {
//            if (schedules.count() == 0) {
//                // 관리자 1명 가져오기
//                User admin = users.findByUsername("admin1")
//                        .orElseThrow(() -> new IllegalStateException("관리자(admin) 계정이 필요합니다."));
//
//                LocalDate today = LocalDate.now(); // 실행일 기준 날짜로 등록하도록
//
//                Schedule s1 = Schedule.builder()
//                        .title("주간 세미나")
//                        .date(today)   // 오늘 날짜
//                        .location("G202")
//                        .description("최신 AI 연구 동향 발표")
//                        .createdBy(admin)
//                        .build();
//                schedules.save(s1);
//
//                Schedule s2 = Schedule.builder()
//                        .title("팀 프로젝트 회의")
//                        .date(today.plusDays(1))
//                        .location("G206")
//                        .description("백엔드 구조 논의")
//                        .createdBy(admin)
//                        .build();
//                schedules.save(s2);
//
//                Schedule s3 = Schedule.builder()
//                        .title("여름 프로젝트 발표")
//                        .date(today.plusDays(2))
//                        .location("밀레홀")
//                        .description("방학 프로젝트 최종 발표")
//                        .createdBy(admin)
//                        .build();
//                schedules.save(s3);
//
//            }
//        };
//    }
//}
