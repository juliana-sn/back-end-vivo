package br.purpletech.vivo.dtos.step;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StepToCreateDTO(
        @NotBlank(message = "Nome da etapa é obrigatório")
        String name,

        @NotBlank(message = "A descrição é obrigatória")
        String description,

        boolean inProgress,

        @NotNull(message = "O número para ordenar a etapa é obrigatório")
        Integer orderStep
) {}
