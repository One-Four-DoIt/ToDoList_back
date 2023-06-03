package com.toDoList.repository.queryDsl;

import com.toDoList.domain.ToDo;
import com.toDoList.domain.User;

import java.util.List;

public interface ToDoQuerydslRepository {
    List<ToDo> findAllOrderByStartDate(String orderBy, User user);
}
