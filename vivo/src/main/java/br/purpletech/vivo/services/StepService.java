package br.purpletech.vivo.services;

import br.purpletech.vivo.models.Step;
import br.purpletech.vivo.models.Task;

import java.util.List;
import java.util.Optional;

public interface StepService {
    Step createStep(Step stepToCreate);
    List<Step> getAllSteps ();
    Optional<Step> getById(Long id);
    Optional<Step> updateStep(Long id, Step updatedStep);
    boolean deleteStep(Long id);

    Step addTask (Long id, Task task);
    boolean deleteTask(Long id_step, Long id_task);

}
