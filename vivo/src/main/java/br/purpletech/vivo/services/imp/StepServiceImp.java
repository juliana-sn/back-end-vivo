package br.purpletech.vivo.services.imp;

import br.purpletech.vivo.dtos.step.StepDTO;
import br.purpletech.vivo.dtos.step.StepToCreateDTO;
import br.purpletech.vivo.dtos.task.TaskToCreateDTO;
import br.purpletech.vivo.models.Step;
import br.purpletech.vivo.models.Task;
import br.purpletech.vivo.repositories.StepRepository;
import br.purpletech.vivo.repositories.TaskRepository;
import br.purpletech.vivo.services.StepService;
import br.purpletech.vivo.utils.EntityDtoConverter;
import br.purpletech.vivo.exceptions.custom.step.*;
import br.purpletech.vivo.exceptions.custom.task.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StepServiceImp implements StepService {
    private final StepRepository stepRepository;

    @Autowired
    private TaskRepository taskRepository;

    public StepServiceImp(StepRepository stepRepository) {
        this.stepRepository = stepRepository;
    }

    @Transactional
    @Override
    public StepDTO createStep(StepToCreateDTO stepToCreate) {
        Step step = EntityDtoConverter.toStep(stepToCreate);
        Step stepSaved = stepRepository.save(step);
        return EntityDtoConverter.toStepDTO(stepSaved);
    }

    @Override
    public StepDTO getById(Long id) {
        Step step = stepRepository.findById(id)
                .orElseThrow(StepNotFoundException::new);

        return EntityDtoConverter.toStepDTO(step);
    }

    @Transactional
    @Override
    public StepDTO updateStep(Long id, StepToCreateDTO updatedStep) {
        Step step = stepRepository.findById(id)
                .orElseThrow(StepNotFoundException::new);

        step.setName(updatedStep.name());
        step.setDescription(updatedStep.description());
        step.setOrder(updatedStep.stepOrder());

        Step stepSaved = stepRepository.save(step);
        return EntityDtoConverter.toStepDTO(stepSaved);
    }

    @Transactional
    @Override
    public void deleteStep(Long id) {
        Step step = stepRepository.findById(id)
                .orElseThrow(StepNotFoundException::new);

        stepRepository.delete(step);
    }

    @Transactional
    @Override
    public StepDTO addTask(Long id, TaskToCreateDTO taskToCreate) {
        Step step = stepRepository.findById(id)
                .orElseThrow(StepNotFoundException::new);

        Task task = EntityDtoConverter.toTask(taskToCreate);
        task.setStep(step);
        Task savedTask = taskRepository.save(task);

        step.getTasks().add(savedTask);
        Step stepSaved = stepRepository.save(step);

        return EntityDtoConverter.toStepDTO(stepSaved);
    }

    @Transactional
    @Override
    public void deleteTask(Long idStep, Long idTask) {
        Step step = stepRepository.findById(idStep)
                .orElseThrow(StepNotFoundException::new);

        Task task = taskRepository.findById(idTask)
                .orElseThrow(TaskNotFoundException::new);

        step.getTasks().remove(task);
        task.setStep(null);

        stepRepository.save(step);
        taskRepository.save(task);
    }

}