package com.toDoList.repository.springDataJpa;

import com.toDoList.domain.ToDo;
import com.toDoList.repository.queryDsl.ToDoQuerydslRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoRepository extends JpaRepository<ToDo, Long>, ToDoQuerydslRepository {
}
