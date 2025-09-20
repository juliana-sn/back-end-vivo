package br.purpletech.vivo.services;

import br.purpletech.vivo.dtos.task.TaskDTO;
import br.purpletech.vivo.dtos.task.TaskToCreateDTO;
import br.purpletech.vivo.models.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<TaskDTO> getAllTasks();

    TaskDTO getById(Long id);

    TaskDTO createTask(TaskToCreateDTO taskToCreate);

    void deleteTask(Long id);

    TaskDTO updateNameTask (Long id, TaskToCreateDTO updateTask);
    public TaskDTO updateStatusTask(Long id);
}
