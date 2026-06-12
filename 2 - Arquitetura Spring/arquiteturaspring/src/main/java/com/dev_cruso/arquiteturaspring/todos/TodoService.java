package com.dev_cruso.arquiteturaspring.todos;

import org.springframework.stereotype.Service;

@Service
public class TodoService {

    private TodoRepository todoRepository;
    private TodoValidate validate;
    private MailSender mailSender;

    public TodoService(TodoRepository todoRepository, TodoValidate validate, MailSender mailSender) {
        this.todoRepository = todoRepository;
        this.validate = validate;
        this.mailSender = mailSender;
    }

    public TodoEntity salvar(TodoEntity newTodoEntity) {
        validate.validar(newTodoEntity);
        return todoRepository.save(newTodoEntity);
    }

    public void atualizarStatus(TodoEntity todo) {
        todoRepository.save(todo);
        String status = todo.getConcluido() == Boolean.TRUE ? "Concluído" : "Incompleto";
        mailSender.enviar("To Do: " + todo.getDescricao() + "foi atualizado para: " + status    );
    }

    public TodoEntity buscarPorId(Integer id) {
        return todoRepository.findById(id).orElse(null);
    }
}
