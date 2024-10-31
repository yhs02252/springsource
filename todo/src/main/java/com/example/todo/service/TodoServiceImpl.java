package com.example.todo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.todo.dto.TodoDto;
import com.example.todo.entity.ToDo;
import com.example.todo.repository.TodoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    @Autowired
    private final TodoRepository todoRepository;

    @Override
    public List<TodoDto> getList(Boolean completed) {

        // List<ToDo> result = todoRepository.findAll();

        // List<TodoDto> list2 = new ArrayList<>();
        // list.forEach(entity -> {
        // list2.add(entityToDto(entity));
        // });

        // 전체 리스트
        // List<TodoDto> list = result
        // .stream()
        // .map(todo -> entityToDto(todo))
        // .collect(Collectors.toList());Boolean completed

        // 미완료 todos / 완료 todos
        List<ToDo> result = todoRepository.findByCompleted(completed);
        List<TodoDto> list = result.stream().map(todo -> entityToDto(todo)).collect(Collectors.toList());

        return list;
    }

    @Override
    public TodoDto getRow(Long id) {
        ToDo toDo = todoRepository.findById(id).get();
        return entityToDto(toDo);
    }

    @Override
    public TodoDto create(TodoDto dto) {
        // dto => entity
        // 클라이언트쪽에서 들어오는 쪽
        ToDo todo = dtoToEntity(dto);

        return entityToDto(todoRepository.save(todo));
    }

    @Override
    public List<TodoDto> getCompleteList() {
        return null;
    }

    @Override
    public Long updateCompleted(TodoDto dto) {
        ToDo todo = todoRepository.findById(dto.getId()).get();
        todo.setCompleted(dto.getCompleted());
        ToDo updateTodo = todoRepository.save(todo);
        return updateTodo.getId();
    }

    @Override
    public void deleteRow(Long id) {
        todoRepository.deleteById(id);
    }

}
