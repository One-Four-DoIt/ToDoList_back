package com.toDoList.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskIdx;
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todoIdx")
    private ToDo toDo;
    @CreationTimestamp
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isFin;
}
