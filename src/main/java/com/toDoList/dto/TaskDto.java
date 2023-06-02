package com.toDoList.dto;

import com.toDoList.domain.Task;
import com.toDoList.domain.ToDo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

public class TaskDto {
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PostTaskDto {
        private Long todoIdx;
        private String title;
        private LocalDateTime endDate;

        public static Task to(ToDo toDo, String title, LocalDateTime endDate) {
            return Task.builder()
                    .toDo(toDo)
                    .title(title)
                    .endDate(endDate)
                    .build();
        }
    }
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateTaskDto {
        private String title;
        private LocalDateTime endDate;
    }
}
