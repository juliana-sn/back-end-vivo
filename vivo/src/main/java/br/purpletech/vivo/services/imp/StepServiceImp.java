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
    public List<StepDTO> getAllSteps() {
        return stepRepository.findAll().stream()
                .map(EntityDtoConverter::toStepDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<StepDTO> getById(Long id) {
        return stepRepository.findById(id).map(EntityDtoConverter::toStepDTO);
    }

    @Transactional
    @Override
    public Optional<StepDTO> updateStep(Long id, StepToCreateDTO updatedStep) {
        return stepRepository.findById(id).map(step -> {
            step.setName(updatedStep.name());
            step.setDescription(updatedStep.description());
            step.setOrder(updatedStep.stepOrder());
            Step stepSaved = stepRepository.save(step);
            return EntityDtoConverter.toStepDTO(stepSaved);
        });
    }

    @Transactional
    @Override
    public boolean deleteStep(Long id) {
        return stepRepository.findById(id).map(step -> {
            stepRepository.delete(step);
            return true;
        }).orElse(false);
    }

    @Transactional
    @Override
    public StepDTO addTask(Long id, TaskToCreateDTO taskToCreate) {
        Optional<Step> stepOptional = Optional.ofNullable(stepRepository.findById(id).orElseThrow(() -> new RuntimeException("Etapa não encontrada")));
        if(stepOptional.isPresent()) {
            Step step = stepOptional.get();
            Task task = EntityDtoConverter.toTask(taskToCreate);
            task.setStep(step);
            Task savedTask = taskRepository.save(task);

            step.getTasks().add(savedTask);
            Step stepSaved = stepRepository.save(step);
            return EntityDtoConverter.toStepDTO(stepSaved);
        }else{
            return null;
        }
    }

    @Transactional
    @Override
    public boolean deleteTask(Long idStep, Long idTask) {
        Optional<Step> stepOptional = Optional.ofNullable(stepRepository.findById(idStep).orElseThrow(() -> new RuntimeException("Etapa não encontrada")));
        Optional<Task> taskOptional = Optional.ofNullable(taskRepository.findById(idTask).orElseThrow(() -> new RuntimeException("Tarefa não encontrada")));
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
