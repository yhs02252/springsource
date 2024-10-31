package com.example.todo.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.todo.entity.ToDo;

@SpringBootTest
public class TodorepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void insertTest() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            ToDo toDo = ToDo.builder()
                    .title("제목" + i)
                    .build();
            todoRepository.save(toDo);
        });
    }

    @Test
    public void selectOneTest() {
        System.out.println(todoRepository.findById(6L).get());
    }

    @Test
    public void selectAllTest() {
        todoRepository.findAll().forEach(todo -> System.out.println(todo));
    }

    @Test
    public void updateTest() {
        ToDo toDo = todoRepository.findById(7L).get();
        toDo.setCompleted(true);
        toDo.setTitle("다른 제목");
        todoRepository.save(toDo);
    }

    @Test
    public void deleteTest() {
        todoRepository.deleteById(3L);
    }

    @Test
    public void completedTest() {
        // 완료된 todos
        todoRepository.findByCompleted(true).forEach(todo -> System.out.println(todo));
        // 미완료 todos
        todoRepository.findByCompleted(false).forEach(todo -> System.out.println(todo));

    }
}
