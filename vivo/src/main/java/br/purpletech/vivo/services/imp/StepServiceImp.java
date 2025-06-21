package br.purpletech.vivo.services.imp;

import br.purpletech.vivo.models.Step;
import br.purpletech.vivo.models.Task;
import br.purpletech.vivo.repositories.StepRepository;
import br.purpletech.vivo.repositories.TaskRepository;
import br.purpletech.vivo.services.StepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StepServiceImp implements StepService {
    private final StepRepository stepRepository;

    @Autowired
    private TaskRepository taskRepository;

    public StepServiceImp(StepRepository stepRepository) {
        this.stepRepository = stepRepository;
    }

    @Override
    public Step createStep(Step stepToCreate) {
        return stepRepository.save(stepToCreate);
    }

    @Override
    public List<Step> getAllSteps() {
        return stepRepository.findAll();
    }

    @Override
    public Optional<Step> getById(Long id) {
        return stepRepository.findById(id);
    }

    @Override
    public Optional<Step> updateStep(Long id, Step updatedStep) {
        return stepRepository.findById(id).map(step -> {
            step.setName(updatedStep.getName());
            step.setDescription(updatedStep.getDescription());
            return stepRepository.save(step);
        });
    }

    @Override
    public boolean deleteStep(Long id) {
        return stepRepository.findById(id).map(step -> {
            stepRepository.delete(step);
            return true;
        }).orElse(false);
    }

    @Override
    public Step addTask(Long id, Task task) {
        Optional<Step> stepOptional = Optional.ofNullable(stepRepository.findById(id).orElseThrow(() -> new RuntimeException("Etapa não encontrada")));
        if(stepOptional.isPresent()) {
            Step step = stepOptional.get();

            Task savedTask = taskRepository.save(task);

            savedTask.setStep(step);
            step.getTasks().add(savedTask);
            return stepRepository.save(step);
        }else{
            return null;
        }
    }

    @Override
    public boolean deleteTask(Long id_step, Long id_task) {
        Optional<Step> stepOptional = Optional.ofNullable(stepRepository.findById(id_step).orElseThrow(() -> new RuntimeException("Etapa não encontrada")));
        Optional<Task> taskOptional = Optional.ofNullable(taskRepository.findById(id_task).orElseThrow(() -> new RuntimeException("Tarefa não encontrada")));
        if(stepOptional.isPresent()) {
            Step step = stepOptional.get();
            Task task = taskOptional.get();

            step.getTasks().remove(task);
            task.setStep(null);
            stepRepository.save(step);
            taskRepository.save(task);
            return true;
        }else{
            return false;
        }
    }
}
