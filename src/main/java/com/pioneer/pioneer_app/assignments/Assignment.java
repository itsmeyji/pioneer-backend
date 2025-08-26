package com.pioneer.pioneer_app.assignments;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "assignments")
@Getter @Setter @NoArgsConstructor
public class Assignment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assignmentId;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private Type type;

    private LocalDate deadline;

    public enum Type {
        STUDY, PROGRAMMING, PROJECT
    }
}
