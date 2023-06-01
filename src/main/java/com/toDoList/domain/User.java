package com.toDoList.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userIdx;
    private String nickName;
    private String email;
    private String password;
    @CreationTimestamp
    private LocalDate createdAt;
    private LocalDate loginAt;
    @ColumnDefault("1")
    private int status;
    @OneToMany(mappedBy = "user")
    List<ToDo> toDos = new ArrayList<>();
}
