package br.purpletech.vivo.controllers;

import br.purpletech.vivo.dtos.task.TaskDTO;
import br.purpletech.vivo.dtos.task.TaskToCreateDTO;
import br.purpletech.vivo.models.Task;
import br.purpletech.vivo.services.imp.TaskServiceImp;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskServiceImp taskServiceImp;


    public TaskController(TaskServiceImp taskServiceImp) {
        this.taskServiceImp = taskServiceImp;
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks(){
        var tasks = taskServiceImp.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id){
        var task = taskServiceImp.getById(id);
        return ResponseEntity.ok(task);
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask (@RequestBody @Valid TaskToCreateDTO taskToCreate){
        var task = taskServiceImp.createTask(taskToCreate);
        return ResponseEntity.ok(task);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskDTO> updateNameTask(@PathVariable Long id, @RequestBody @Valid TaskToCreateDTO updatedTask){
        var task = taskServiceImp.updateNameTask(id, updatedTask);
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        taskServiceImp.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
