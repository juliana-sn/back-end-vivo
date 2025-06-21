package br.purpletech.vivo.controllers;

import br.purpletech.vivo.models.Step;
import br.purpletech.vivo.models.Task;
import br.purpletech.vivo.services.imp.StepServiceImp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/steps")
public class StepController {
    private final StepServiceImp stepServiceImp;


    public StepController(StepServiceImp stepServiceImp) {
        this.stepServiceImp = stepServiceImp;
    }

    @PostMapping
    public ResponseEntity<Step> createStep(@RequestBody Step stepToCreate){
        var step = stepServiceImp.createStep(stepToCreate);
        return ResponseEntity.ok(step);
    }

    @GetMapping
    public ResponseEntity<List<Step>> getAllSteps(){
        var steps = stepServiceImp.getAllSteps();
        return ResponseEntity.ok(steps);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Step>> getStepById(@PathVariable Long id){
        var step = stepServiceImp.getById(id);
        return ResponseEntity.ok(step);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<Step>> updateStep(@PathVariable Long id, @RequestBody Step updatedStep){
        var step = stepServiceImp.updateStep(id, updatedStep);
        return ResponseEntity.ok(step);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStep(@PathVariable Long id){
        stepServiceImp.deleteStep(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/tasks")
    public ResponseEntity<Step> addTask(@PathVariable Long id, @RequestBody Task task){
        var step = stepServiceImp.addTask(id, task);
        return ResponseEntity.ok(step);
    }

    @DeleteMapping("/{id}/tasks/{id_task}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id, @PathVariable Long id_task){
        stepServiceImp.deleteTask(id, id_task);
        return ResponseEntity.noContent().build();
    }
}
