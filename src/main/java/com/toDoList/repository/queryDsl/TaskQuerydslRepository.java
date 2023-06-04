package com.toDoList.repository.queryDsl;

import com.toDoList.domain.ToDo;
import com.toDoList.dto.TaskDto.SelectTask;

import java.util.List;

public interface TaskQuerydslRepository {
    List<SelectTask> findAllOrderBy(String orderBy, ToDo toDo);
}
