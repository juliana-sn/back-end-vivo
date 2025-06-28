package br.purpletech.vivo.services.imp;

import br.purpletech.vivo.dtos.task.TaskDTO;
import br.purpletech.vivo.dtos.task.TaskToCreateDTO;
import br.purpletech.vivo.models.Task;
import br.purpletech.vivo.repositories.TaskRepository;
import br.purpletech.vivo.services.TaskService;
import br.purpletech.vivo.utils.EntityDtoConverter;
import br.purpletech.vivo.exceptions.custom.task.TaskNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImp implements TaskService {
    private final TaskRepository taskRepository;

    public TaskServiceImp(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findByStandardTrue().stream()
                .map(EntityDtoConverter::toTaskDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDTO getById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(TaskNotFoundException::new);

        return EntityDtoConverter.toTaskDTO(task);
    }

    @Transactional
    @Override
    public TaskDTO createTask(TaskToCreateDTO taskToCreate) {
        Task task = EntityDtoConverter.toTask(taskToCreate);
        Task taskSaved = taskRepository.save(task);
        return EntityDtoConverter.toTaskDTO(taskSaved);
    }

    @Transactional
    @Override
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(TaskNotFoundException::new);

        taskRepository.delete(task);
    }


    @Transactional
    @Override
    public TaskDTO updateNameTask(Long id, TaskToCreateDTO updatedTask) {
        Task task = taskRepository.findById(id)
                .orElseThrow(TaskNotFoundException::new);

        task.setName(updatedTask.name());
        Task updated = taskRepository.save(task);

        return EntityDtoConverter.toTaskDTO(updated);
    }

    @Transactional
    @Override
    public TaskDTO updateStatusTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(TaskNotFoundException::new);

        task.setCompleted(true);
        Task updated = taskRepository.save(task);

        return EntityDtoConverter.toTaskDTO(updated);
    }

}
