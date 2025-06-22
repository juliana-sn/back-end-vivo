package br.purpletech.vivo.controllers;

import br.purpletech.vivo.models.Task;
import br.purpletech.vivo.services.imp.TaskServiceImp;
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
    public ResponseEntity<List<Task>> getAllTasks(){
        var tasks = taskServiceImp.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Task>> getTaskById(@PathVariable Long id){
        var task = taskServiceImp.getById(id);
        return ResponseEntity.ok(task);
    }

    @PostMapping
    public ResponseEntity<Task> createTask (@RequestBody Task taskToCreate){
        var task = taskServiceImp.createTask(taskToCreate);
        return ResponseEntity.ok(task);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Optional<Task>> updateNameTask(@PathVariable Long id, @RequestBody Task updatedTask){
        var task = taskServiceImp.updateNameTask(id, updatedTask);
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        taskServiceImp.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
