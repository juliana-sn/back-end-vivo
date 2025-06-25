package br.purpletech.vivo.controllers;

import br.purpletech.vivo.dtos.step.StepDTO;
import br.purpletech.vivo.dtos.step.StepToCreateDTO;
import br.purpletech.vivo.dtos.task.TaskToCreateDTO;
import br.purpletech.vivo.models.Step;
import br.purpletech.vivo.models.Task;
import br.purpletech.vivo.services.imp.StepServiceImp;
import jakarta.validation.Valid;
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
    public ResponseEntity<StepDTO> createStep(@RequestBody @Valid StepToCreateDTO stepToCreate){
        var step = stepServiceImp.createStep(stepToCreate);
        return ResponseEntity.ok(step);
    }

    @GetMapping
    public ResponseEntity<List<StepDTO>> getAllSteps(){
        var steps = stepServiceImp.getAllSteps();
        return ResponseEntity.ok(steps);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<StepDTO>> getStepById(@PathVariable Long id){
        var step = stepServiceImp.getById(id);
        return ResponseEntity.ok(step);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Optional<StepDTO>> updateStep(@PathVariable Long id, @RequestBody @Valid StepToCreateDTO updatedStep){
        var step = stepServiceImp.updateStep(id, updatedStep);
        return ResponseEntity.ok(step);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStep(@PathVariable Long id){
        stepServiceImp.deleteStep(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/tasks")
    public ResponseEntity<StepDTO> addTask(@PathVariable Long id, @RequestBody @Valid TaskToCreateDTO task){
        var step = stepServiceImp.addTask(id, task);
        return ResponseEntity.ok(step);
    }

    @DeleteMapping("/{id}/tasks/{idTask}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id, @PathVariable Long idTask){
        stepServiceImp.deleteTask(id, idTask);
        return ResponseEntity.noContent().build();
    }
}
