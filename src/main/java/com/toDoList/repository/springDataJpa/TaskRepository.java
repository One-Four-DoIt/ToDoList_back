package com.toDoList.repository.springDataJpa;

import com.toDoList.domain.Task;
import com.toDoList.repository.queryDsl.TaskQuerydslRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long>, TaskQuerydslRepository {
}
