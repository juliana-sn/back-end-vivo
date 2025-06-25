package br.purpletech.vivo.services.imp;

import br.purpletech.vivo.dtos.task.TaskDTO;
import br.purpletech.vivo.dtos.task.TaskToCreateDTO;
import br.purpletech.vivo.models.Task;
import br.purpletech.vivo.repositories.TaskRepository;
import br.purpletech.vivo.services.TaskService;
import br.purpletech.vivo.utils.EntityDtoConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImp implements TaskService {
    private final TaskRepository taskRepository;

    public TaskServiceImp(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(EntityDtoConverter::toTaskDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TaskDTO> getById(Long id) {
        return taskRepository.findById(id).map(EntityDtoConverter::toTaskDTO);
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
    public boolean deleteTask(Long id) {
        return taskRepository.findById(id).map(task -> {
            taskRepository.delete(task);
            return true;
        }).orElse(false);
    }

    @Transactional
    @Override
    public Optional<TaskDTO> updateNameTask(Long id, TaskToCreateDTO updatedTask) {
        return taskRepository.findById(id).map(task -> {
            task.setName(updatedTask.name());
            Task taskUpdated = taskRepository.save(task);
            return EntityDtoConverter.toTaskDTO(taskUpdated);
        });
    }
}
