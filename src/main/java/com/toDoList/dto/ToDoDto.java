package com.toDoList.dto;

import com.toDoList.domain.ToDo;
import com.toDoList.domain.User;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class ToDoDto {
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SaveToDoRequest{
        private String title;
        private LocalDateTime endDate;

        public static ToDo to(User user, String title, LocalDateTime endDate){
            return ToDo.builder()
                    .user(user)
                    .title(title)
                    .endDate(endDate)
                    .build();
        }
    }
}
