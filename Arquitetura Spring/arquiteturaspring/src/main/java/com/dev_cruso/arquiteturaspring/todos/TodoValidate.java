package com.dev_cruso.arquiteturaspring.todos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TodoValidate {

    private TodoRepository todoRepository;

    @Autowired
    public TodoValidate(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public void validar(TodoEntity todo) {
        if(existeTodoComDescricao(todo.getDescricao())) {
            throw new IllegalArgumentException(("Já existe um TODO com essa descrição!"));
        }
    }

    private boolean existeTodoComDescricao(String descricao) {
        return todoRepository.existsByDescricao(descricao);
    }
}
