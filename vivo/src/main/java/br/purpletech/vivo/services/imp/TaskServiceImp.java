package br.purpletech.vivo.services.imp;

import br.purpletech.vivo.models.Task;
import br.purpletech.vivo.repositories.TaskRepository;
import br.purpletech.vivo.services.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImp implements TaskService {
    private final TaskRepository taskRepository;

    public TaskServiceImp(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Optional<Task> getById(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public Task createTask(Task taskToCreate) {
        return taskRepository.save(taskToCreate);
    }

    @Override
    public boolean deleteTask(Long id) {
        return taskRepository.findById(id).map(task -> {
            taskRepository.delete(task);
            return true;
        }).orElse(false);
    }

    @Override
    public Optional<Task> updateNameTask(Long id, Task updatedTask) {
        return taskRepository.findById(id).map(task -> {
            task.setName(updatedTask.getName());
            return taskRepository.save(task);
        });
    }
}
