package com.toDoList.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    @JoinColumn(name = "toDoIdx")
    private ToDo toDo;
    @CreationTimestamp
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @ColumnDefault("0")
    private boolean isFin;

    public void checkIsFin() {
        this.isFin = true;
    }

    public void unCheckIsFin() {
        this.isFin = false;
    }

    public void updateTask(String title,LocalDateTime endDate) {
        this.title=title;
        this.endDate=endDate;
    }

}
