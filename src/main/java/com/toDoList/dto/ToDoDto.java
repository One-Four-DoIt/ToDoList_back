package com.toDoList.dto;

import com.toDoList.domain.Task;
import com.toDoList.domain.ToDo;
import com.toDoList.domain.User;
import com.toDoList.dto.TaskDto.SelectTask;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


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

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SelectToDo {
        private Long toDoIdx;
        private String title;
        private LocalDateTime endDate;
        private boolean isFin;
        private List<SelectTask> selectTasks;

        public static SelectToDo from(ToDo toDo) {
            return SelectToDo.builder()
                    .toDoIdx(toDo.getToDoIdx())
                    .title(toDo.getTitle())
                    .endDate(toDo.getEndDate())
                    .isFin(toDo.isFin())
                    .selectTasks(toSelectTasks(toDo.getTasks()))
                    .build();
        }

        private static List<SelectTask> toSelectTasks(List<Task> tasks) {
            List<SelectTask> selectTasks = new ArrayList<>();

            for (int i = 0; i < tasks.size(); i++) {
                selectTasks.add(SelectTask.from(tasks.get(i)));
            }

            return selectTasks;
        }

    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateToDoRequest {
        private String title;
        private LocalDateTime endDate;
    }
}

