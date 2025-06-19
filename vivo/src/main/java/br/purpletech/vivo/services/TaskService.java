package br.purpletech.vivo.services;

import br.purpletech.vivo.models.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Task> getAllTasks();

    Optional<Task> getById(Long id);

    Task createTask(Task taskToCreate);

    boolean deleteTask(Long id);

    Optional<Task> updateNameTask (Long id, Task updateTask);
}
