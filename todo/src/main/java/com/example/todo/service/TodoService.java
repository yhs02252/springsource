package com.example.todo.service;

import java.util.List;

import com.example.todo.dto.TodoDto;
import com.example.todo.entity.ToDo;

public interface TodoService {
    List<TodoDto> getList(Boolean completed);

    TodoDto getRow(Long id);

    TodoDto create(TodoDto dto);

    List<TodoDto> getCompleteList();

    Long updateCompleted(TodoDto dto);

    void deleteRow(Long id);

    // dto ==> Entity
    public default ToDo dtoToEntity(TodoDto dto) {
        return ToDo.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .completed(dto.getCompleted())
                .important(dto.getImportant())
                .build();
    }

    // Entity ==> dto
    public default TodoDto entityToDto(ToDo entity) {
        return TodoDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .completed(entity.getCompleted())
                .important(entity.getImportant())
                .build();
    }
}
