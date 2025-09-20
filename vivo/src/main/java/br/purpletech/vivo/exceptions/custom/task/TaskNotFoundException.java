package br.purpletech.vivo.exceptions.custom.task;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException() {
        super("Tarefa não encontrada.");
    }
}
