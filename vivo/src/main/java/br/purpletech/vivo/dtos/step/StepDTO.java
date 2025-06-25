package br.purpletech.vivo.dtos.step;

import br.purpletech.vivo.dtos.onboarding.OnboardingDTO;
import br.purpletech.vivo.dtos.task.TaskDTO;

import java.util.List;

public record StepDTO(
        Long id,
        String name,
        String description,
        Integer orderStep, // novo campo para ordenar
        List<TaskDTO> tasks
) {}
