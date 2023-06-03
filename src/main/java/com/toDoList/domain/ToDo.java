package com.toDoList.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class ToDo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long toDoIdx;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userIdx")
    private User user;
    @OneToMany(mappedBy = "toDo")
    private List<Task> tasks = new ArrayList<>();
    private String title;
    @CreationTimestamp
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean isFin;


}

