package br.purpletech.vivo.dtos.task;

import br.purpletech.vivo.dtos.step.StepDTO;

public record TaskDTO(
        Long id,
        String name,
        boolean standard,
        boolean completed
) {}
