package br.purpletech.vivo.services;

import br.purpletech.vivo.dtos.step.StepDTO;
import br.purpletech.vivo.dtos.step.StepToCreateDTO;
import br.purpletech.vivo.dtos.task.TaskToCreateDTO;
import br.purpletech.vivo.models.Step;
import br.purpletech.vivo.models.Task;

import java.util.List;
import java.util.Optional;

public interface StepService {
    StepDTO createStep(StepToCreateDTO stepToCreate);
    List<StepDTO> getAllSteps ();
    StepDTO getById(Long id);
    StepDTO updateStep(Long id, StepToCreateDTO updatedStep);
    void deleteStep(Long id);

    StepDTO addTask (Long id, TaskToCreateDTO task);
    void deleteTask(Long idStep, Long idTask);

}
