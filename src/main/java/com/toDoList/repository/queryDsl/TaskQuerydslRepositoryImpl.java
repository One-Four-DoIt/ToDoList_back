package com.toDoList.repository.queryDsl;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toDoList.domain.ToDo;
import com.toDoList.dto.TaskDto.SelectTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.toDoList.domain.QTask.task;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TaskQuerydslRepositoryImpl implements TaskQuerydslRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<SelectTask> findAllOrderBy(String orderBy, ToDo toDo) {
        List<SelectTask> tasks = queryFactory.select(Projections.constructor(SelectTask.class,
                        task.taskIdx,
                        task.title,
                        task.endDate,
                        task.isFin))
                .from(task)
                .where(task.toDo.eq(toDo))
                .orderBy(orderSpecifier(orderBy))
                .fetch();

        return tasks;
    }

    private OrderSpecifier<?> orderSpecifier(String orderBy) {
        switch (orderBy) {
            case "newest" :
                return new OrderSpecifier<>(Order.DESC, task.startDate);
            case "oldest" :
                return new OrderSpecifier<>(Order.ASC, task.startDate);
        }
        return null;
    }
}
