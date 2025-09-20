package br.purpletech.vivo.dtos.task;

import jakarta.validation.constraints.NotBlank;

public record TaskToCreateDTO(
        @NotBlank(message = "O nome da task é obrigatório")
        String name,
        boolean standard
) {}
