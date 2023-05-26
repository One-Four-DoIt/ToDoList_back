package com.toDoList.repository.springDataJpa;

import com.toDoList.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
