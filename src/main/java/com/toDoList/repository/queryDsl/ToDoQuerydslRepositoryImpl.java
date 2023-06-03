package com.toDoList.repository.queryDsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toDoList.domain.QToDo;
import com.toDoList.domain.ToDo;
import com.toDoList.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.toDoList.domain.QToDo.toDo;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ToDoQuerydslRepositoryImpl implements ToDoQuerydslRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<ToDo> findAllOrderByStartDate(String orderBy, User user) {
        List<ToDo> toDos = queryFactory.selectFrom(toDo)
                .where(toDo.user.eq(user))
                .orderBy(orderSpecifier(orderBy))
                .fetch();

        return toDos;
    }

    private OrderSpecifier<?> orderSpecifier(String orderBy) {
        switch (orderBy) {
            case "newest" :
                return new OrderSpecifier<>(Order.DESC, toDo.startDate);
            case "oldest" :
                return new OrderSpecifier<>(Order.ASC, toDo.startDate);
            case "deadline" :
                return new OrderSpecifier<>(Order.ASC, toDo.endDate);
        }
        return null;
    }
}
